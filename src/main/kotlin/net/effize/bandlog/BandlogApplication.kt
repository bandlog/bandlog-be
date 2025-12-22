package net.effize.bandlog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BandlogApplication

fun main(args: Array<String>) {
    runApplication<BandlogApplication>(*args)
}
