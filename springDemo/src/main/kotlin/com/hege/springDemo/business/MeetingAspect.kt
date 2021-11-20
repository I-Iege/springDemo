package com.hege.springDemo.business

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.context.annotation.Configuration

@Aspect
@Configuration
class MeetingAspect {
    @Before("within(com.hege.springDemo.rest.*Controller)")
    fun meetingControllerPointcut(joinPoint: JoinPoint) {
        println("${joinPoint.signature.name} called")
    }
}