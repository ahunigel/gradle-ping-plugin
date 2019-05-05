package com.github.ahunigel

import org.gradle.api.Plugin
import org.gradle.api.Project

class PingPlugin implements Plugin<Project> {

    private pingStatusDetermined = false

    @Override
    void apply(Project project) {

        project.extensions.create('ping', PingPluginExtension)

        project.task('ping') {
            doLast {
                ping(project)
            }
        }
        // project.getTasks().getByName('clean').dependsOn('ping')
        project.configurations.each {
            it.incoming.beforeResolve {
                if (!pingStatusDetermined) {
                    ping(project)
                    pingStatusDetermined = true
                }
            }
        }
    }

    void ping(Project project) {
        def baseHost = project.ping.host
        def pingTimeout = project.ping.timeout
        def pingToggleOfflineMode = project.ping.toggleOfflineMode
        try {
            if (InetAddress.getByName(baseHost).isReachable(pingTimeout)) {
                project.ping.reachable = true
                toggleOfflineMode(project, pingToggleOfflineMode, false)
                println "Host ${baseHost} is reachable"
            } else {
                toggleOfflineMode(project, pingToggleOfflineMode, true)
                println "Host ${baseHost} is not reachable"
            }
        } catch (UnknownHostException e) {
            println "Unknown host ${baseHost}"
            toggleOfflineMode(project, pingToggleOfflineMode, true)
            return
        } catch (all) {
            println "Host ${baseHost} is not reachable, beacuse ${all}"
        }
        project.logger.info "${baseHost} reachable is ${project.ping.reachable}"
        if (!project.ping.reachable) {
            try {
                println "Try to ping ${baseHost} directly"
                project.exec {
                    commandLine 'cmd', '/c', "ping ${baseHost}"
                }
                project.ping.reachable = true
                toggleOfflineMode(project, pingToggleOfflineMode, false)
            } catch (all) {
                toggleOfflineMode(project, pingToggleOfflineMode, true)
                println "Host ${baseHost} is not reachable, beacuse ${all}"
            }
        }
        project.logger.info "Host ${baseHost} reachable is ${project.ping.reachable}"
    }

    void toggleOfflineMode(Project project, boolean pingToggleOfflineMode, boolean toOfflineMode) {
        if (pingToggleOfflineMode && project.gradle.startParameter.offline != toOfflineMode) {
            project.gradle.startParameter.offline = toOfflineMode
        }
    }
}