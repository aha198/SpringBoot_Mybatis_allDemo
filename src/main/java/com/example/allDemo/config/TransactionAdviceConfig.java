package com.example.allDemo.config;
import org.springframework.context.annotation.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.Advisor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import java.util.Collections;

import com.example.allDemo.commons.*;
@Configuration
//@Aspect:作用是把当前类标识为一个切面供容器读取
@Aspect
public class TransactionAdviceConfig extends BaseConfig {
  //声明一个常量，需要拦截的方法-用切点语言来写
  private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.gongrongfei.allDemo..service.*Service.*(..))";

  @Autowired
  private PlatformTransactionManager transactionManager;

  @Bean
  public TransactionInterceptor txAdvice() {

      RuleBasedTransactionAttribute txAttr_REQUIRED = new RuleBasedTransactionAttribute();
      txAttr_REQUIRED.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
      //设置事务的传播行为，TransactionDefinition.PROPAGATION_REQUIRED
      //当前有事务，就加入这个事务，没有事务，就新建一个事务
      txAttr_REQUIRED.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
      //设置隔离级别，可重复读取
      //可重复读取是指在一个事务内，多次读同一个数据，在这个事务还没结束时，其他事务不能访问该数据(包括了读写)，这样就可以在同一个事务内两次读到的数据是一样的，因此称为是可重复读隔离级别
      //txAttr_REQUIRED.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
      RuleBasedTransactionAttribute txAttr_REQUIREDNEW = new RuleBasedTransactionAttribute();
      txAttr_REQUIREDNEW.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
      //设置事务的传播行为，TransactionDefinition.PROPAGATION_REQUIRES_NEW
      //新建一个事务执行，如果当前有事务，就把当前的事务挂起
      txAttr_REQUIREDNEW.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

      NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

      source.addTransactionalMethod("*", txAttr_REQUIRED);
      source.addTransactionalMethod("newTran*", txAttr_REQUIREDNEW);

      return new TransactionInterceptor(transactionManager, source);
  }

  @Bean
  public Advisor txAdviceAdvisor() {
      //声明一个aspectj切点
      AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
      //设置需要拦截的方法
      pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
      //在该切点，执行txAdvice()方法
      return new DefaultPointcutAdvisor(pointcut, txAdvice());
  }

}
