package com.buaa.edu.concurrent.dp.future;

public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        
        Data data = client.request("a");//�ͻ��˻Ὺ��һ���߳�ȥִ����������
        System.out.println("�������");
        try {
        	//���������һ��sleep�����˶�����ҵ���߼��Ĵ���
            System.out.println("Doing another start");
            Thread.sleep(2000);
            System.out.println("Doing another done");
        } catch (InterruptedException e) {
        }
        	//ʹ����ʵ������
        System.out.println("���� = " + data.getResult());
    }
}
