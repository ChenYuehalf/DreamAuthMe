package ltd.dreamcraft.dreamauthme;

import fr.xephi.authme.command.PlayerCommand;
import ltd.dreamcraft.dreamauthme.event.PlayerAuthMeEvent;
import ltd.dreamcraft.dreamauthme.event.handleCustomPacketEvent;
import ltd.dreamcraft.dreamauthme.manager.DataManager;
import ltd.dreamcraft.dreamauthme.manager.command;
import ltd.dreamcraft.dreamauthme.tools.CheckPluginUpdate;
import ltd.dreamcraft.dreamauthme.tools.Metrics;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import priv.seventeen.artist.dreampainter.api.DreamPainterScreenAPI;
import priv.seventeen.artist.dreampainter.api.screen.DreamPainterScreen;

import static org.bukkit.Bukkit.getConsoleSender;

public final class DreamAuthMe extends JavaPlugin {
    public static DreamAuthMe in() {
        return getPlugin(DreamAuthMe.class);
    }

    public static DreamPainterScreen DreamAuthMeLogin;
    public static DreamPainterScreen DreamAuthMeReg;
    public static DreamPainterScreen DreamAuthMeChangePsd;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new handleCustomPacketEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerAuthMeEvent(), this);
        getConsoleSender().sendMessage("��3 ____                              _         _   _     __  __    ");
        getConsoleSender().sendMessage("��3|  _ \\ _ __ ___  __ _ _ __ ___    / \\  _   _| |_| |__ |  \\/  | ___ ");
        getConsoleSender().sendMessage("��3| | | | '__/ _ \\/ _` | '_ ` _ \\  / _ \\| | | | __| '_ \\| |\\/| |/ _ \\");
        getConsoleSender().sendMessage("��3| |_| | | |  __/ (_| | | | | | |/ ___ \\ |_| | |_| | | | |  | |  __/");
        getConsoleSender().sendMessage("��3|____/|_|  \\___|\\__,_|_| |_| |_/_/   \\_\\__,_|\\__|_| |_|_|  |_|\\___|");
        getConsoleSender().sendMessage("��b|> ��e��ӭʹ��DreamAuthMe����ʦ������¼���!");
        getConsoleSender().sendMessage("��b|> ��e��ǰ�汾Ϊ��" + getDescription().getVersion());
        //������ ��������ļ�������true�� ������Ƿ���Ҫ���� �� ע�������ҵ�¼�¼��������op�ͷ��͸�����ʾ
        CheckPluginUpdate.checkPluginUpdate("https://pluginapi.dreamcraft.ltd/api/plugins/");
        Plugin authMePlugin = getServer().getPluginManager().getPlugin("AuthMe");
        if (authMePlugin != null && authMePlugin.isEnabled()) {
            // AuthMeǰ�ò�������ڲ����ã���ʼ�� AuthMe API
//            authMeApi = AuthMeApi.getInstance();
        } else {
            getLogger().warning("AuthMe ���δ�ҵ���δ���á�DragonAuthMe ���������á�");
            getServer().getPluginManager().disablePlugin(this);
        }
        this.getCommand("DreamAuthMe").setExecutor(new command());
        //��ʼ�������ļ�
        DataManager d = new DataManager();
        d.init();
        //�ڲ����ע��screen
        DreamAuthMeLogin = new DreamPainterScreen(this, "screen/DreamAuthMeLogin");
        DreamPainterScreenAPI.registerScreen(DreamAuthMeLogin);
        DreamAuthMeReg = new DreamPainterScreen(this, "screen/DreamAuthMeReg");
        DreamPainterScreenAPI.registerScreen(DreamAuthMeReg);
        DreamAuthMeChangePsd = new DreamPainterScreen(this, "screen/DreamAuthMeChangePsd");
        DreamPainterScreenAPI.registerScreen(DreamAuthMeChangePsd);
        DreamAuthMeReg.reload();
        DreamAuthMeChangePsd.reload();
        DreamAuthMeLogin.reload();
        getConsoleSender().sendMessage("��b|> ��e�ɹ�����3��screen");
        //ͳ��
        Metrics metrics = new Metrics(this, 19743);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        DreamPainterScreenAPI.unRegisterScreen(DreamAuthMeLogin);
        DreamPainterScreenAPI.unRegisterScreen(DreamAuthMeReg);
        DreamPainterScreenAPI.unRegisterScreen(DreamAuthMeChangePsd);
        getLogger().info("�����ж��,screenҳ���ѱ�ɾ��");
    }
}
