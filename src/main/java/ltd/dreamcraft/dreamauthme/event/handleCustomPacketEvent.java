package ltd.dreamcraft.dreamauthme.event;

import fr.xephi.authme.api.v3.AuthMeApi;
import ltd.dreamcraft.dreamauthme.DreamAuthMe;
import ltd.dreamcraft.dreamauthme.method;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import priv.seventeen.artist.dreampainter.event.ClientCustomPacketEvent;

import java.util.HashSet;
import java.util.Set;

import static ltd.dreamcraft.dreamauthme.DreamAuthMe.*;

public class handleCustomPacketEvent implements Listener {
    private final AuthMeApi authMeApi = AuthMeApi.getInstance();
    private Set<String> safeUsers = new HashSet<>();

    @EventHandler(priority = EventPriority.LOWEST)
    public void CustomPacketEvent(ClientCustomPacketEvent event) {
//        ˵��: ��һ���б��͵������ ��һ��������ʶ��ID �ڶ������б����
//                - English -> Packet.send('MyPacket',$list)
//                - ���� -> ͨѶ.����('MyPacket',$list)
//                - ����(Return) -> void
        if (!event.getId().equals("DreamAuthMe")) {
            return;
        }
        Player player = event.getPlayer();
        String action = event.getData(0).getString();
        if ("login".equalsIgnoreCase(action)) {
            String password = event.getData(1).getString();
            if (password == null || "".equalsIgnoreCase(password)) {
                DreamAuthMeLogin.runShimmerCode(player, "�첽������Ϣ('�������������');");
                return;
            }
            if (password.length() < 4 || password.length() > 30) {
                DreamAuthMeLogin.runShimmerCode(player, "�첽������Ϣ('����λ������');");
                return;
            }
            if (!authMeApi.checkPassword(player.getName(), password)) {
                DreamAuthMeLogin.runShimmerCode(player, "�첽������Ϣ('�������,�����������');");
                return;
            }
            authMeApi.forceLogin(player);
            DreamAuthMeLogin.close(player);
        } else if ("register".equalsIgnoreCase(action)) {
            String password = event.getData(1).getString();
            //���书�ܹر���ֱ��ע�ᣬ������֤�����֤
//                player.sendMessage(Lang.success("�˺�ע��ɹ�"));

            if (password == null || "".equalsIgnoreCase(password)) {
                DreamAuthMeReg.runShimmerCode(player, "�첽������Ϣ('�������������');");
                return;
            }
            if (password.length() < 4 || password.length() > 30) {
                DreamAuthMeReg.runShimmerCode(player, "�첽������Ϣ('����λ������');");
                return;
            }
            method.forcePlayerToRegister(player.getPlayer(), password);
//                    DreamAuthMe.in().getServer().getScheduler().runTaskLater(DreamAuthMe.in(), () -> authMeApi.forceLogin(player), 2L);
            DreamAuthMeReg.close(player);
            return;
        } else if ("changePsd".equalsIgnoreCase(action)) {
            String oldPassword = event.getData(1).getString();
            String newPassword = event.getData(2).getString();
            if (!authMeApi.checkPassword(player.getName(), oldPassword)) {
                DreamAuthMeChangePsd.runShimmerCode(player, "�첽������Ϣ('���������');");
                return;
            }
            if (oldPassword == null || "".equalsIgnoreCase(oldPassword) || newPassword == null || "".equalsIgnoreCase(newPassword)) {
                DreamAuthMeChangePsd.runShimmerCode(player, "�첽������Ϣ('�������������');");
                return;
            }
            if (oldPassword.length() < 4 || oldPassword.length() > 30 || newPassword.length() < 4 || newPassword.length() > 30) {
                DreamAuthMeChangePsd.runShimmerCode(player, "�첽������Ϣ('����λ������');");
                return;
            }
            authMeApi.changePassword(player.getName(), newPassword);
            DreamAuthMe.in().getServer().getScheduler().runTaskLater(DreamAuthMe.in(), () -> authMeApi.forceLogin(player), 2L);
            DreamAuthMeChangePsd.close(player);
        } else if ("regmessage".equalsIgnoreCase(action)) {
            String message = event.getData(1).getString();
            DreamAuthMeLogin.runShimmerCode(player, "�첽������Ϣ('" + message + "');");
            return;
        } else if ("cpsdmessage".equalsIgnoreCase(action)) {
            String message = event.getData(1).getString();
            DreamAuthMeChangePsd.runShimmerCode(player, "�첽������Ϣ('" + message + "');");
            return;
        }
    }
}
