<nav class="navbar navbar-default navbar-static-top">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="javascript:;">Dashboard</a>
    </div>
    <div id="navbar" class="navbar-collapse collapse">
      <ul class="nav navbar-nav">
        <li class="dropdown <#if viewData.page_global_navIndex gt 10 && viewData.page_global_navIndex < 20 >active</#if>">
          <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Actuator <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li class="dropdown-header">spring-boot-actuator</li>
            <li class="<#if viewData.page_global_navIndex==11.1>active</#if>"><a href="${viewData.page_global_dashboardPathPrefix}/dashboard/env"><i class="icon icon-server"></i>&nbsp;Environment</a></li>
            <li class="<#if viewData.page_global_navIndex==11.2>active</#if>"><a href="${viewData.page_global_dashboardPathPrefix}/dashboard/mappings"><i class="icon icon-list"></i>&nbsp;Request Mappings</a></li>
            <li class="<#if viewData.page_global_navIndex==11.3>active</#if>"><a href="${viewData.page_global_dashboardPathPrefix}/dashboard/beans"><i class="icon icon-leaf"></i>&nbsp;Beans</a></li>
            <li role="separator" class="divider"></li>
            <li class="<#if viewData.page_global_navIndex==11.4>active</#if>"><a href="${viewData.page_global_dashboardPathPrefix}/dashboard/loggers"><i class="icon icon-sliders"></i>&nbsp;Loggers</a></li>
            <li class="<#if viewData.page_global_navIndex==11.5>active</#if>"><a href="${viewData.page_global_dashboardPathPrefix}/dashboard/trace"><i class="icon icon-eye-open"></i>&nbsp;Trace</a></li>
            <li class="<#if viewData.page_global_navIndex==11.6>active</#if>"><a href="${viewData.page_global_dashboardPathPrefix}/dashboard/threads"><i class="icon icon-list"></i>&nbsp;Threads</a></li>
            <li role="separator" class="divider"></li>
            <li class="<#if viewData.page_global_navIndex==11.7>active</#if>"><a href="${viewData.page_global_dashboardPathPrefix}/dashboard/details"><i class="icon icon-info"></i>&nbsp;Details</a></li>
            <!--
            <li class="<#if viewData.page_global_navIndex==11.61>active</#if>"><a href="${viewData.page_global_dashboardPathPrefix}/dashboard/metrics"><i class="icon icon-bar-chart"></i>&nbsp;Metrics</a></li>
            -->
          </ul>
        </li>

        <li class="dropdown <#if viewData.page_global_navIndex gt 20 && viewData.page_global_navIndex < 30 >active</#if>">
          <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Schedule <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li class="<#if viewData.page_global_navIndex==21.1>active</#if>"><a href="${viewData.page_global_dashboardPathPrefix}/dashboard/scheduledjobs"><i class="icon icon-time"></i>&nbsp;Scheduled Jobs</a></li>
          </ul>
        </li>

        <li class="dropdown <#if viewData.page_global_navIndex gt 30 && viewData.page_global_navIndex < 40 >active</#if>">
          <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Web <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li class="<#if viewData.page_global_navIndex==31.1>active</#if>"><a href="${viewData.page_global_dashboardPathPrefix}/dashboard/corsconfig"><i class="icon icon-time"></i>&nbsp;Cors Config</a></li>
          </ul>
        </li>

        <li class="dropdown">
          <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Stat <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="${viewData.page_global_serverContextPath}/admin/druid/" target="_blank"><i class="icon icon-database"></i>&nbsp;Druid Stat</a></li>
            <li><a href="${viewData.page_global_serverContextPath}/admin/requestcapture/" target="_blank"><i class="icon icon-dashboard"></i>&nbsp;Request Capture Stat</a></li>
          </ul>
        </li>
      </ul>

      <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="icon icon-user"></i>&nbsp;admin&nbsp;<span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="${viewData.page_global_serverContextPath}/logout"><i class="icon icon-off"></i>&nbsp;Exit</a></li>
          </ul>
        </li>
      </ul>
      <!--
      <form class="navbar-form navbar-right"><input type="text" class="form-control" placeholder="Search..."></form>
      -->
    </div>
  </div>
</nav>