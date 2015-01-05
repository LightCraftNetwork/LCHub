package com.lightcraftmc.coinapi;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.lightcraftmc.hub.main.Main;
import com.lightcraftmc.rank.RankManager;

public class ApiEvent implements Listener {
	
	private static Main plugin;
	public static boolean hasInit = false;
	private static int count = 0;
	private static String current = "";
	static String title = "§9§l§e§lL§9§light§3§lCraft §9§lL§e§li§9§lght§3§lCraft §9§lLi§e§lg§9§lht§3§lCraft §9§lLig§e§lh§9§lt§3§lCraft §9§lLigh§e§lt§3§lCraft §9§lLight§3§l§e§lC§3§lraft §9§lLight§3§lC§e§lr§3§laft §9§lLight§3§lCr§e§la§3§lft §9§lLight§3§lCra§e§lf§3§lt §9§lLight§3§lCraf§e§lt §9§lLight§3§lCraft";
	
	public static void initialize(){
		ApiEvent.plugin = Main.getInstance();
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable(){
			@SuppressWarnings("deprecation")
			public void run(){
				getNext();
				for(Player p : Bukkit.getOnlinePlayers()) scoreboard(p, current);
			}
		}, 0, 5);
	}
	
	public static String getNext(){
		count++;
		if(count > getTitle().length - 1){
			count = 0;
		}
		current = getTitle()[count];
		return getTitle()[count];
	}
	
	public static String[] getTitle(){
		return title.split(" ");
	}
	public static void scoreboard(Player p){
		scoreboard(p, current);
	}
	@SuppressWarnings("deprecation")
	public static void scoreboard(Player p, String t){
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		for(Player p2 : Bukkit.getOnlinePlayers())try{ board.getTeam(p2.getName()).unregister(); }catch(Exception ex){}

		Objective objective = board.registerNewObjective("Test2", "dummy");
		objective.setDisplayName(t);
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);

		

		int a = LcTokensAPI.balancePoints(p);
		int b = LcCoinsAPI.balancePoints(p);
		
		for (Player checkstaff : Bukkit.getOnlinePlayers()) {
			if (checkstaff.isOp()) {
				Score staffyes = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "" + ChatColor.BOLD + "YES"));
				staffyes.setScore(10);
				break;
			} else if (!checkstaff.isOp()){
				Score staffno = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.DARK_RED + "" + ChatColor.BOLD + "NO"));
				staffno.setScore(10);
				break;
			}
		}
		
	//	Score topline = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.AQUA + "-------------"));
		Score score = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.LIGHT_PURPLE + "Tokens "));
		Score pixels = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.WHITE + "" + a));
		Score spacing = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.LIGHT_PURPLE + " "));
		Score score2 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Coins "));
		Score coins = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.WHITE + "" + b));
		Score spacing3 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.DARK_AQUA + ""));
		Score score3 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "Rank "));
		Score rank = objective.getScore(Bukkit.getOfflinePlayer(RankManager.getColor(RankManager.getRank(p)) + WordUtils.capitalize(RankManager.getRank(p).toString().toLowerCase())));
		Score spacing4 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.RED + ""));
		Score staff = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BLUE + "Staff Online?"));
		//	Score botmline = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.AQUA + "-------------"));

		//	topline.setScore(21);
		score.setScore(20);
		pixels.setScore(19);
		spacing.setScore(18);
		score2.setScore(17);
		coins.setScore(16);
		spacing3.setScore(15);
		score3.setScore(14);
		rank.setScore(13);
		spacing4.setScore(12);
		staff.setScore(11);
		//	botmline.setScore(9);
		p.setScoreboard(board);

	}
	
	@EventHandler
	public void pickup(PlayerPickupItemEvent e) {
		scoreboard(e.getPlayer());
	}
	
	public static void updatescore(Player players) {
		scoreboard(players);
	}
	
	@EventHandler
	public void join(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (p.hasPlayedBefore()) {
			scoreboard(p);
		} else if (!p.hasPlayedBefore()) {
			LcCoinsAPI.givePoints(p, 1000);
			LcTokensAPI.givePoints(p, 100);
			//com.lightcraftmc.mysql.MySQL.addIntoDB(p);
			scoreboard(p);
		}
	}
	
	/*@SuppressWarnings("deprecation")
	@EventHandler
	public void onKill(EntityDeathEvent e) {
		Player player = (Player) e.getEntity();
        if(player.getKiller() instanceof Player) {
            Player killer = player.getKiller();
			PixlPointsAPI.givePoints(killer, 1);
			killer.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Points ")).setScore(PixlPointsAPI.balancePoints(killer));
        }
	}*/
}
