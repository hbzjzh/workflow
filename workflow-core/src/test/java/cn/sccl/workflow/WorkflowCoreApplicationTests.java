package cn.sccl.workflow;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WorkflowCoreApplication.class)
public class WorkflowCoreApplicationTests {

		@Test
		public void contextLoads() {

		}

		@Autowired
		private ProcessEngine processEngine;

		@Test
		public void deployProcessDefinition_zip() {
				InputStream in = this.getClass().getClassLoader().getResourceAsStream("bpmn/leave.bpmn");
				Deployment deployment = processEngine.getRepositoryService().createDeployment().name("请假").addInputStream("leave.bpmn", in).deploy();

		}

		@Test
		public void startProcessInstance(){
				//1、流程定义的key，通过这个key来启动流程实例
				String processDefinitionKey = "hello";
				//2、与正在执行的流程实例和执行对象相关的Service
				// startProcessInstanceByKey方法还可以设置其他的参数，比如流程变量。
				ProcessInstance pi = processEngine.getRuntimeService()
						.startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
				log.info("流程实例ID:"+pi.getId());//流程实例ID
				log.info("流程定义ID:"+pi.getProcessDefinitionId());//流程定义ID
		}

}
