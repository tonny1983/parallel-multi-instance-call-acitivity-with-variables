package cc.tonny.bpmn.camunda.parallelmultiinstancecallacitivitywithvariables;

import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.hamcrest.MatcherAssert;
import org.junit.ClassRule;
import org.junit.Rule;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.job;
import static org.hamcrest.CoreMatchers.equalTo;

@Deployment(resources = {
        "bpmn/parent-process.bpmn",
        "bpmn/sub-process.bpmn"
})
public class Test {
    @Rule
    @ClassRule
    public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();

    @org.junit.Test
    public void normal() {
        // given
        String processDefinitionKey = "parent-process";

        // when
        var processInstance = runtimeService().startProcessInstanceByKey(processDefinitionKey);

        // then
        assertThat(processInstance).isNotEnded();
        execute(job());
        assertThat(processInstance).isEnded();

        var total = historyService()
                .createHistoricVariableInstanceQuery()
                .processInstanceId(processInstance.getId())
                .variableName("total")
                .singleResult()
                .getValue();

        var anotherTotal = historyService()
                .createHistoricVariableInstanceQuery()
                .processInstanceId(processInstance.getId())
                .variableName("anotherTotal")
                .singleResult()
                .getValue();

        MatcherAssert.assertThat(total, equalTo(anotherTotal));
    }
}
