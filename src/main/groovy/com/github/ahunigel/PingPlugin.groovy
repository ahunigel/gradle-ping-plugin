package com.github.ahunigel

import org.gradle.api.Plugin
import org.gradle.api.Project

class PingPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.extensions.create('ping', PingPluginExtension)

        project.task('ping') {
            doLast {
                ping(project)
            }
        }
        // project.getTasks().getByName('clean').dependsOn('ping')
    }

    void ping(Project project) {
        def baseHost = project.ping.host
        def pingTimeout = project.ping.timeout
        try {
            if (InetAddress.getByName(baseHost).isReachable(pingTimeout)) {
                project.ping.reachable = true
                println "Host ${baseHost} is reachable"
            } else {
                println "Host ${baseHost} is not reachable"
            }
        } catch (UnknownHostException e) {
            println "Unknown host ${baseHost}"
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
            } catch (all) {
                println "Host ${baseHost} is not reachable, beacuse ${all}"
            }
        }
        project.logger.info "Host ${baseHost} reachable is ${project.ping.reachable}"
    }
}