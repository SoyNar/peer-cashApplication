package com.peercash.PeerCashproject.Utils;

import com.peercash.PeerCashproject.Models.AuditEntity;
import com.peercash.PeerCashproject.Repository.AuditRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditRepository auditRepository;
    private final HttpServletRequest request;

    @Pointcut("@annotation(com.peercash.PeerCashproject.Utils.Auditable)")
    private void auditableMethod(){};

    @Around("auditableMethod() && @annotation(auditable)")
    public Object auditMethod(ProceedingJoinPoint joinPoint,  Auditable auditable) throws Throwable {
        long startTime = System.currentTimeMillis();
        String username = "System";
        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        // cuando use spring security

        //intenta ejecutar metodo
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        Object result;
        String status = "SUCCES";
        String errorDetails  = null;

        try{
            result = joinPoint.proceed();
        } catch (Throwable e) {
           status = "ERROR";
           errorDetails = e.getMessage();
           throw e;
        } finally {

            long duration = System.currentTimeMillis() - startTime;
            AuditEntity auditEntity = AuditEntity.builder()
                    .nameEntity(auditable.entity().isEmpty() ? className
                            : auditable.entity())
                    .action(auditable.action().isEmpty() ? methodName : auditable.action() )
                    .createAt(LocalDateTime.now())
                    .updateAt(LocalDateTime.now())
                    .ipAddress(ipAddress)
                    .userAgent(userAgent)
                    .status(status)
                    .details(errorDetails)
                    .description(methodName + " de " + className)
                    .duration(duration)
                    .build();
            auditRepository.save(auditEntity);

        }
        return  result;
    }
}
