package ltd.dreamcraft.dreamauthme.manager;

import ltd.dreamcraft.dreamauthme.DreamAuthMe;

import java.io.File;

import static org.bukkit.Bukkit.getConsoleSender;

public class DataManager {
    public void init() {
        initScreen();
    }

    private void initScreen() {
        File folder = new File("plugins/DreamAuthMe/screen");

        // ����ļ����Ƿ���ڣ�����������򴴽���
        if (!folder.exists()) {
            if (folder.mkdirs()) {
//                getConsoleSender().sendMessage("��b|> ��e�ɹ����� 'plugins/DreamAuthMe/screen' �ļ���!");
            } else {
                getConsoleSender().sendMessage("��c|> ��4�޷����� 'plugins/DreamAuthMe/screen' �ļ���!");
                return; // ����޷������ļ��У��˳������������������ʧ��
            }
        }

        // ������鲢���������ļ�
        File file1 = new File(folder, "DreamAuthMeLogin.yml");
        File file2 = new File(folder, "DreamAuthMeReg.yml");
        File file3 = new File(folder, "DreamAuthMeChangePsd.yml");

        if (!file1.exists()) {
            DreamAuthMe.in().saveResource("screen/DreamAuthMeLogin.yml", false);
        }
        if (!file2.exists()) {
            DreamAuthMe.in().saveResource("screen/DreamAuthMeReg.yml", false);
        }
        if (!file3.exists()) {
            DreamAuthMe.in().saveResource("screen/DreamAuthMeChangePsd.yml", false);
            getConsoleSender().sendMessage("��b|> ��e����Ĭ�ϵ�screen����!");
        }

    }

}
