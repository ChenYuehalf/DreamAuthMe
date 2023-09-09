package ltd.dreamcraft.dreamauthme.event;

import fr.xephi.authme.api.v3.AuthMeApi;
import ltd.dreamcraft.dreamauthme.DreamAuthMe;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class PlayerAuthMeEvent implements Listener {
    private final AuthMeApi authMeApi = AuthMeApi.getInstance();
    private Set<String> safeUsers = new HashSet<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // ��ȡ��Ҷ���
        Player player = event.getPlayer();
        System.out.println("��Ҽ����˷�����" + player.getName());
        // ʹ����ʱ����� GUI
        new BukkitRunnable() {
            @Override
            public void run() {
                if (authMeApi.isRegistered(player.getName())) {
                    openLoginGui(player);
                } else {
                    openRegGui(player);
                }
                System.out.println(player.getPlayer() + "�����˷��������Ҵ���gui����");
            }
        }.runTaskLater(DreamAuthMe.in(), 40);
    }

    private void openRegGui(Player player) {
        // �������Ƿ�����
        if (!player.isOnline()) {
            return;
        }

        // �������Ƿ���ͨ�������֤
        if (authMeApi.isAuthenticated(player)) {
            return;
        }

        // ���ʹ� GUI �����ݰ�
        DreamAuthMe.DreamAuthMeReg.open(player);
    }

    private void openLoginGui(Player player) {
        // �������Ƿ�����
        if (!player.isOnline()) {
            return;
        }

        // �������Ƿ���ͨ�������֤
        if (authMeApi.isAuthenticated(player)) {
            return;
        }

        // ���ʹ� GUI �����ݰ�
        DreamAuthMe.DreamAuthMeLogin.open(player);
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                if (!safeUsers.contains(player.getName())) {
//                    openGui(player);
//                }
//            }
//        }.runTaskLater(DreamAuthMe.in(), 20);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // �� safeUsers �������Ƴ��뿪�����
        safeUsers.remove(event.getPlayer().getName());
    }

}
