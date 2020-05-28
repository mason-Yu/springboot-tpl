/**
 * 
 */
package com.hanhan.store.generated.web;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.ModelAndView;

import com.github.jerryxia.devutil.SystemClock;

import com.hanhan.store.generated.RuntimeVars;
import lombok.val;

/**
 * @author JerryXia
 *
 */
@Controller
@RequestMapping(path = "${management.context-path}")
public class ManagementDashboardController extends BaseWebMvcController {
    private String serverContextPath;
    private String managementContextPath;
    private String dashboardPathPrefix;

    /**
     * related with schedule
     */
    private Field ScheduledTask_futureField;
    private String reschedulingRunnableClassName = "org.springframework.scheduling.concurrent.ReschedulingRunnable";
    private Field ReschedulingRunnable_triggerContextField;
    private Field ReschedulingRunnable_triggerField;

    private CorsConfigurationService corsConfigurationService;

    @PostConstruct
    public void init() {
        this.serverContextPath = RuntimeVars.env.getProperty("server.context-path", "");
        this.managementContextPath = RuntimeVars.env.getProperty("management.context-path", "");
        this.dashboardPathPrefix = this.serverContextPath + managementContextPath;

        ScheduledTask_futureField = ReflectionUtils.findField(ScheduledTask.class, "future");
        ReflectionUtils.makeAccessible(ScheduledTask_futureField);
        try {
            Class<?> reschedulingRunnableClass = Class.forName(reschedulingRunnableClassName);
            ReschedulingRunnable_triggerContextField = ReflectionUtils.findField(reschedulingRunnableClass,
                    "triggerContext");
            ReflectionUtils.makeAccessible(ReschedulingRunnable_triggerContextField);
            ReschedulingRunnable_triggerField = ReflectionUtils.findField(reschedulingRunnableClass, "trigger");
            ReflectionUtils.makeAccessible(ReschedulingRunnable_triggerField);
        } catch (ClassNotFoundException e) {

        }
        try {
            this.corsConfigurationService = RuntimeVars.applicationContext.getBean(CorsConfigurationService.class);
        } catch (BeansException ex) {
            this.corsConfigurationService = RuntimeVars.DEFAULT_CORS_CONFIGURATION_SERVICE;
        }
    }

    public String dashboardPath() {
        return this.dashboardPathPrefix + "/dashboard/";
    }

    @GetMapping(value = "/dashboard/")
    public String indexRedirect() {
        return "redirect:" + this.managementContextPath + "/dashboard/index";
    }

    @GetMapping(value = "/dashboard/index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("management/dashboard/index");
        val viewData = tkd(mv, "", "", "");
        setPageGlobal(viewData);
        setNavIndex(viewData, 0);
        return mv;
    }

    @GetMapping(value = "/dashboard/env")
    public ModelAndView env() {
        ModelAndView mv = new ModelAndView("management/dashboard/env");
        val viewData = tkd(mv, "Environment", "", "");
        setPageGlobal(viewData);
        setNavIndex(viewData, 11.1);
        return mv;
    }

    @GetMapping(value = "/dashboard/mappings")
    public ModelAndView mappings() {
        ModelAndView mv = new ModelAndView("management/dashboard/mappings");
        val viewData = tkd(mv, "Request Mappings", "", "");
        setPageGlobal(viewData);
        setNavIndex(viewData, 11.2);
        return mv;
    }

    @GetMapping(value = "/dashboard/beans")
    public ModelAndView beans() {
        ModelAndView mv = new ModelAndView("management/dashboard/beans");
        val viewData = tkd(mv, "Beans", "", "");
        setPageGlobal(viewData);
        setNavIndex(viewData, 11.3);
        return mv;
    }

    @GetMapping(value = "/dashboard/loggers")
    public ModelAndView loggers() {
        ModelAndView mv = new ModelAndView("management/dashboard/loggers");
        val viewData = tkd(mv, "Loggers", "", "");
        setPageGlobal(viewData);
        setNavIndex(viewData, 11.4);
        return mv;
    }

    @GetMapping(value = "/dashboard/trace")
    public ModelAndView trace() {
        ModelAndView mv = new ModelAndView("management/dashboard/trace");
        val viewData = tkd(mv, "Trace", "", "");
        setPageGlobal(viewData);
        setNavIndex(viewData, 11.5);
        return mv;
    }

    @GetMapping(value = "/dashboard/threads")
    public ModelAndView threads() {
        ModelAndView mv = new ModelAndView("management/dashboard/threads");
        val viewData = tkd(mv, "Threads", "", "");
        setPageGlobal(viewData);
        setNavIndex(viewData, 11.6);
        return mv;
    }

    @GetMapping(value = "/dashboard/details")
    public ModelAndView details() {
        ModelAndView mv = new ModelAndView("management/dashboard/details");
        val viewData = tkd(mv, "Details", "", "");
        setPageGlobal(viewData);
        setNavIndex(viewData, 11.7);
        return mv;
    }

    @GetMapping(value = "/dashboard/metrics")
    public ModelAndView metrics() {
        ModelAndView mv = new ModelAndView("management/dashboard/metrics");
        val viewData = tkd(mv, "Metrics", "", "");
        setPageGlobal(viewData);
        setNavIndex(viewData, 11.61);
        return mv;
    }

    @GetMapping(value = "/dashboard/scheduledjobs")
    public ModelAndView scheduledjobs() {
        ModelAndView mv = new ModelAndView("management/dashboard/scheduledjobs");
        val viewData = tkd(mv, "Scheduled Jobs", "", "");
        setPageGlobal(viewData);
        setNavIndex(viewData, 21.1);
        return mv;
    }

    @GetMapping(value = "/dashboard/corsconfig")
    public ModelAndView corsConfig() {
        ModelAndView mv = new ModelAndView("management/dashboard/corsconfig");
        val viewData = tkd(mv, "Cors Config", "", "");
        setPageGlobal(viewData);
        setNavIndex(viewData, 31.1);
        return mv;
    }

    @GetMapping(value = "/dashboard/corsconfig/rules")
    @ResponseBody
    public Map<String, CorsConfiguration> corsConfigRules() {
        return corsConfigurationService.load();
    }

    @PostMapping(value = "/dashboard/corsconfig/rule")
    @ResponseBody
    public HashMap<String, Object> corsConfigRuleModify(@RequestBody CorsConfiguration corsConfiguration,
            HttpServletRequest httpServletRequest) {
        String urlPath = httpServletRequest.getParameter("urlPath");
        corsConfigurationService.write(urlPath, corsConfiguration);
        return new HashMap<String, Object>();
    }

    @GetMapping(value = "/dashboard/scheduledjobs/registeredTasks")
    @ResponseBody
    public HashMap<String, ArrayList<RegisteredTask>> scheduledjobsRegisteredTasks() {
        HashMap<String, ArrayList<RegisteredTask>> registeredTasksMap = new HashMap<String, ArrayList<RegisteredTask>>();
        ArrayList<RegisteredTask> cronTasks = new ArrayList<RegisteredTask>();
        ArrayList<RegisteredTask> fixedDelayTasks = new ArrayList<RegisteredTask>();
        ArrayList<RegisteredTask> fixedRateTasks = new ArrayList<RegisteredTask>();

        List<CronTask> cronTaskList = RuntimeVars.taskRegistrar.getCronTaskList();
        for (CronTask cronTask : cronTaskList) {
            String id = null, name = null, declaringClass = null;
            TriggerContext triggerContext = null;
            Date nextExecutionTime = null;
            if (cronTask.getRunnable() instanceof ScheduledMethodRunnable) {
                ScheduledMethodRunnable scheduledMethodRunnable = (ScheduledMethodRunnable) cronTask.getRunnable();
                name = scheduledMethodRunnable.getMethod().getName();
                declaringClass = scheduledMethodRunnable.getMethod().getDeclaringClass().getName();
                id = declaringClass + "." + name;

                if (ReschedulingRunnable_triggerContextField != null && cronTask.getTrigger() instanceof CronTrigger) {
                    Set<ScheduledTask> scheduledTasks = RuntimeVars.scheduledTasks
                            .get(scheduledMethodRunnable.getTarget());
                    for (ScheduledTask scheduledTask : scheduledTasks) {
                        Object scheduledTask_Future = ReflectionUtils.getField(ScheduledTask_futureField,
                                scheduledTask);
                        if (reschedulingRunnableClassName.equals(scheduledTask_Future.getClass().getName())) {
                            Object reschedulingRunnable_triggerField = ReflectionUtils
                                    .getField(ReschedulingRunnable_triggerField, scheduledTask_Future);
                            if (reschedulingRunnable_triggerField == cronTask.getTrigger()) {
                                Object reschedulingRunnable_triggerContext = ReflectionUtils
                                        .getField(ReschedulingRunnable_triggerContextField, scheduledTask_Future);
                                triggerContext = (TriggerContext) reschedulingRunnable_triggerContext;
                                nextExecutionTime = ((CronTrigger) cronTask.getTrigger())
                                        .nextExecutionTime(triggerContext);
                                break;
                            }
                        }
                    }
                }
            }
            cronTasks.add(new RegisteredTask(id, name, declaringClass, "CronTask", cronTask.getExpression(),
                    nextExecutionTime, triggerContext.lastScheduledExecutionTime(),
                    triggerContext.lastActualExecutionTime(), triggerContext.lastCompletionTime(), -1, -1));
        }

        List<IntervalTask> fixedDelayTaskList = RuntimeVars.taskRegistrar.getFixedDelayTaskList();
        for (IntervalTask fixedDelayTask : fixedDelayTaskList) {
            String id = null, name = null, declaringClass = null;
            if (fixedDelayTask.getRunnable() instanceof ScheduledMethodRunnable) {
                ScheduledMethodRunnable scheduledMethodRunnable = (ScheduledMethodRunnable) fixedDelayTask
                        .getRunnable();
                name = scheduledMethodRunnable.getMethod().getName();
                declaringClass = scheduledMethodRunnable.getMethod().getDeclaringClass().getName();
                id = declaringClass + "." + name;
            }
            fixedDelayTasks.add(new RegisteredTask(id, name, declaringClass, "IntervalTask", null, null, null, null,
                    null, fixedDelayTask.getInterval(), fixedDelayTask.getInitialDelay()));
        }

        List<IntervalTask> fixedRateTaskList = RuntimeVars.taskRegistrar.getFixedRateTaskList();
        for (IntervalTask fixedRateTask : fixedRateTaskList) {
            String id = null, name = null, declaringClass = null;
            if (fixedRateTask.getRunnable() instanceof ScheduledMethodRunnable) {
                ScheduledMethodRunnable scheduledMethodRunnable = (ScheduledMethodRunnable) fixedRateTask.getRunnable();
                name = scheduledMethodRunnable.getMethod().getName();
                declaringClass = scheduledMethodRunnable.getMethod().getDeclaringClass().getName();
                id = declaringClass + "." + name;
            }
            fixedRateTasks.add(new RegisteredTask(id, name, declaringClass, "IntervalTask", null, null, null, null,
                    null, fixedRateTask.getInterval(), fixedRateTask.getInitialDelay()));
        }
        registeredTasksMap.put("cronTasks", cronTasks);
        registeredTasksMap.put("fixedDelayTasks", fixedDelayTasks);
        registeredTasksMap.put("fixedRateTasks", fixedRateTasks);
        return registeredTasksMap;
    }

    @PostMapping(value = "/dashboard/scheduledjobs/triggerRegisteredTasks")
    @ResponseBody
    public String scheduledjobsTriggerRegisteredTasks(String[] cronTasks, String[] fixedDelayTasks,
            String[] fixedRateTasks) {
        HashMap<String, Runnable> registerdTasks = new HashMap<String, Runnable>();
        List<CronTask> cronTaskList = RuntimeVars.taskRegistrar.getCronTaskList();
        for (CronTask cronTask : cronTaskList) {
            if (cronTask.getRunnable() instanceof ScheduledMethodRunnable) {
                ScheduledMethodRunnable scheduledMethodRunnable = (ScheduledMethodRunnable) cronTask.getRunnable();
                String id = scheduledMethodRunnable.getMethod().getDeclaringClass().getName() + "."
                        + scheduledMethodRunnable.getMethod().getName();
                registerdTasks.put(id, cronTask.getRunnable());
            }
        }
        List<IntervalTask> fixedDelayTaskList = RuntimeVars.taskRegistrar.getFixedDelayTaskList();
        for (IntervalTask fixedDelayTask : fixedDelayTaskList) {
            if (fixedDelayTask.getRunnable() instanceof ScheduledMethodRunnable) {
                ScheduledMethodRunnable scheduledMethodRunnable = (ScheduledMethodRunnable) fixedDelayTask
                        .getRunnable();
                String id = scheduledMethodRunnable.getMethod().getDeclaringClass().getName() + "."
                        + scheduledMethodRunnable.getMethod().getName();
                registerdTasks.put(id, fixedDelayTask.getRunnable());
            }
        }
        List<IntervalTask> fixedRateTaskList = RuntimeVars.taskRegistrar.getFixedRateTaskList();
        for (IntervalTask fixedRateTask : fixedRateTaskList) {
            if (fixedRateTask.getRunnable() instanceof ScheduledMethodRunnable) {
                ScheduledMethodRunnable scheduledMethodRunnable = (ScheduledMethodRunnable) fixedRateTask.getRunnable();
                String id = scheduledMethodRunnable.getMethod().getDeclaringClass().getName() + "."
                        + scheduledMethodRunnable.getMethod().getName();
                registerdTasks.put(id, fixedRateTask.getRunnable());
            }
        }

        ThreadPoolTaskScheduler taskScheduler = RuntimeVars.applicationContext.getBean(
                ScheduledAnnotationBeanPostProcessor.DEFAULT_TASK_SCHEDULER_BEAN_NAME, ThreadPoolTaskScheduler.class);
        for (String taskId : cronTasks) {
            Runnable command = registerdTasks.get(taskId);
            if (command != null) {
                taskScheduler.schedule(command, SystemClock.nowDate());
            }
        }
        for (String taskId : fixedDelayTasks) {
            Runnable command = registerdTasks.get(taskId);
            if (command != null) {
                taskScheduler.schedule(command, SystemClock.nowDate());
            }
        }
        for (String taskId : fixedRateTasks) {
            Runnable command = registerdTasks.get(taskId);
            if (command != null) {
                taskScheduler.schedule(command, SystemClock.nowDate());
            }
        }
        return "{\"code\":\"1\"}";
    }

    private void setPageGlobal(HashMap<String, Object> viewData) {
        viewData.put("page_global_serverContextPath", this.serverContextPath);
        viewData.put("page_global_dashboardPathPrefix", this.dashboardPathPrefix);
    }

    private void setNavIndex(HashMap<String, Object> viewData, double navIndex) {
        viewData.put("page_global_navIndex", navIndex);
    }

    class RegisteredTask {
        private final String id;
        private final String name;
        private final String declaringClass;
        private final String type;

        private final String expression;
        private final Date nextExecutionTime;
        private final Date lastScheduledExecutionTime;
        private final Date lastActualExecutionTime;
        private final Date lastCompletionTime;

        private final long interval;
        private final long initialDelay;

        public RegisteredTask(String id, String name, String declaringClass, String type, String expression,
                Date nextExecutionTime, Date lastScheduledExecutionTime, Date lastActualExecutionTime,
                Date lastCompletionTime, long interval, long initialDelay) {
            super();
            this.id = id;
            this.name = name;
            this.declaringClass = declaringClass;
            this.type = type;
            this.expression = expression;
            this.nextExecutionTime = nextExecutionTime;
            this.lastScheduledExecutionTime = lastScheduledExecutionTime;
            this.lastActualExecutionTime = lastActualExecutionTime;
            this.lastCompletionTime = lastCompletionTime;
            this.interval = interval;
            this.initialDelay = initialDelay;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDeclaringClass() {
            return declaringClass;
        }

        public String getType() {
            return type;
        }

        public String getExpression() {
            return expression;
        }

        public Date getNextExecutionTime() {
            return nextExecutionTime;
        }

        public Date getLastScheduledExecutionTime() {
            return lastScheduledExecutionTime;
        }

        public Date getLastActualExecutionTime() {
            return lastActualExecutionTime;
        }

        public Date getLastCompletionTime() {
            return lastCompletionTime;
        }

        public long getInterval() {
            return interval;
        }

        public long getInitialDelay() {
            return initialDelay;
        }
    }
}
