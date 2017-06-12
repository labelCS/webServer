echo mysql.driver_class=com.mysql.jdbc.Driver > %~dp0aa\db.properties

echo mysql.jdbcurl="jdbc:mysql://localhost:3306/sva?autoReconncet=true&useUnicode=true&characterEncoding=utf8" >> %~dp0aa\db.properties

echo mysql.user=root >> %~dp0aa\db.properties
echo mysql.password=123456 >> %~dp0aa\db.properties
echo mysql.db=sva >> %~dp0aa\db.properties

pause