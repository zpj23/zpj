package com.thread;

public class User {
	private String code;
	private int cash;
	User(String code,int cash){
		this.code=code;
		this.cash=cash;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getCash() {
		return cash;
	}
	public void setCash(int cash) {
		this.cash = cash;
	}
	public synchronized void oper(int x) { 
        try { 
                Thread.sleep(10L); 
                this.cash += x; 
                System.out.println(Thread.currentThread().getName() + "运行结束，增加“" + x + "”，当前用户账户余额为：" + cash); 
                Thread.sleep(10L); 
        } catch (InterruptedException e) { 
                e.printStackTrace(); 
        } 
	} 

    @Override 
    public String toString() { 
            return "User{" + 
                            "code='" + code + '\'' + 
                            ", cash=" + cash + 
                            '}'; 
    } 
	
}
