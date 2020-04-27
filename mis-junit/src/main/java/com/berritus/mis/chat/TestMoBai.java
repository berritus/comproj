package com.berritus.mis.chat;

import java.util.Scanner;
/**
 * @Description:
 * @Copyright: mis520
 * @Author: Qin Guihe
 * @Date: Create in 2020/4/27
 */
public class TestMoBai {
    public static void main(String[] args) throws Exception {
        //创建一个聊天客户端对象
        ChatClient chatClient = new ChatClient();
        new Thread() { //单独开一个线程不断的接收服务器端广播的数据
            @Override
            public void run() {
                while (true) {
                    chatClient.receiveMsg();
                    try { //间隔 3 秒
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        Scanner scanner = new Scanner(System.in);
        //在控制台输入数据并发送到服务器端
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            chatClient.sendMsg(msg);
        }
    }
}
