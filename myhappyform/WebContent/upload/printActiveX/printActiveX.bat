echo off
color 3f
echo.
echo ������ Intranet����
:: �����á��Ա��Ϊ�ɰ�ȫִ�нű��� ActiveX �ؼ�ִ�нű�
@reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings\Zones\1" /v "1405" /t REG_DWORD /d 0 /f
:: �����á���δ���Ϊ�ɰ�ȫִ�нű���ActiveX�ؼ���ʼ����ִ���нű�
@reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings\Zones\1" /v "1201" /t REG_DWORD /d 0 /f
echo.
echo ������վ������
:: �����á��Ա��Ϊ�ɰ�ȫִ�нű��� ActiveX �ؼ�ִ�нű�
@reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings\Zones\2" /v "1405" /t REG_DWORD /d 0 /f
:: �����á���δ���Ϊ�ɰ�ȫִ�нű���ActiveX�ؼ���ʼ����ִ���нű�
@reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings\Zones\2" /v "1201" /t REG_DWORD /d 0 /f
 
echo   �������Ժ��Զ��ر�...
ping 127.0.0.1 -n 10 >nul 2>nul
exit