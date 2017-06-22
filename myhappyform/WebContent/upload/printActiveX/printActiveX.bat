echo off
color 3f
echo.
echo 【本地 Intranet区域】
:: 【启用】对标记为可安全执行脚本的 ActiveX 控件执行脚本
@reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings\Zones\1" /v "1405" /t REG_DWORD /d 0 /f
:: 【启用】对未标记为可安全执行脚本的ActiveX控件初始化并执行行脚本
@reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings\Zones\1" /v "1201" /t REG_DWORD /d 0 /f
echo.
echo 【可信站点区域】
:: 【启用】对标记为可安全执行脚本的 ActiveX 控件执行脚本
@reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings\Zones\2" /v "1405" /t REG_DWORD /d 0 /f
:: 【启用】对未标记为可安全执行脚本的ActiveX控件初始化并执行行脚本
@reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings\Zones\2" /v "1201" /t REG_DWORD /d 0 /f
 
echo   本程序稍后自动关闭...
ping 127.0.0.1 -n 10 >nul 2>nul
exit