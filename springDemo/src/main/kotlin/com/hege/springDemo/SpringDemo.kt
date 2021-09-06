package com.hege.springDemo

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableAutoConfiguration(exclude=[DataSourceAutoConfiguration::class, XADataSourceAutoConfiguration::class])
class SpringDemo

fun main(args: Array<String>) {
	runApplication<SpringDemo>(*args)
}
