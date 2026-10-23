set JAVA_HOME=C:\Program Files\Java\jdk-21
set PATH=%JAVA_HOME%\bin;%PATH% 
set JMETER_HOME=C:\Prog\apache-jmeter-5.6.3
%JMETER_HOME%\bin\jmeter -n -t appliSpringWebRestApi.jmx -l summary-report.jtl
REM -n for no_gui (cli mode)
REM -t test_plan.jmx
REM -l log_file.jtl