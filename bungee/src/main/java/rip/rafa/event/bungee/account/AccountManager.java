package rip.rafa.event.bungee.account;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import rip.rafa.event.bungee.EventBungee;
import rip.rafa.event.bungee.rank.Rank;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AccountManager {
    
    private static final Map<UUID, Account> accounts = new ConcurrentHashMap<>();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static File dataFolder;
    
    public static void init(EventBungee plugin) {
        dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        loadAll();
    }
    
    public static Account get(UUID uuid) {
        return accounts.get(uuid);
    }
    
    public static Account getByName(String name) {
        return accounts.values().stream()
                .filter(acc -> acc.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
    
    public static void load(UUID uuid, String name) {
        Account account = loadFromFile(uuid);
        
        if (account == null) {
            account = new Account(uuid, name);
            saveToFile(account);
        } else {
            if (!account.getName().equals(name)) {
                account.setName(name);
                saveToFile(account);
            }
        }
        
        accounts.put(uuid, account);
    }
    
    public static void unload(UUID uuid) {
        Account account = accounts.remove(uuid);
        if (account != null) {
            saveToFile(account);
        }
    }
    
    public static void save(Account account) {
        saveToFile(account);
    }
    
    private static Account loadFromFile(UUID uuid) {
        File file = new File(dataFolder, "accounts/" + uuid.toString() + ".json");
        if (!file.exists()) {
            return null;
        }
        
        try (Reader reader = new FileReader(file)) {
            AccountData data = gson.fromJson(reader, AccountData.class);
            Account account = new Account(uuid, data.name);
            account.setRank(Rank.fromString(data.rank));
            return account;
        } catch (IOException e) {
            EventBungee.getInstance().getLogger().severe("Erro ao carregar conta: " + e.getMessage());
            return null;
        }
    }
    
    private static void saveToFile(Account account) {
        File accountsDir = new File(dataFolder, "accounts");
        if (!accountsDir.exists()) {
            accountsDir.mkdirs();
        }
        
        File file = new File(accountsDir, account.getUuid().toString() + ".json");
        
        try (Writer writer = new FileWriter(file)) {
            AccountData data = new AccountData();
            data.uuid = account.getUuid().toString();
            data.name = account.getName();
            data.rank = account.getRank().name();
            
            gson.toJson(data, writer);
        } catch (IOException e) {
            EventBungee.getInstance().getLogger().severe("Erro ao salvar conta: " + e.getMessage());
        }
    }
    
    private static void loadAll() {
        File accountsDir = new File(dataFolder, "accounts");
        if (!accountsDir.exists()) {
            return;
        }
        
        File[] files = accountsDir.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null) {
            return;
        }
        
        for (File file : files) {
            try (Reader reader = new FileReader(file)) {
                AccountData data = gson.fromJson(reader, AccountData.class);
                UUID uuid = UUID.fromString(data.uuid);
                Account account = new Account(uuid, data.name);
                account.setRank(Rank.fromString(data.rank));
                accounts.put(uuid, account);
            } catch (IOException e) {
                EventBungee.getInstance().getLogger().severe("Erro ao carregar conta: " + e.getMessage());
            }
        }
        
        EventBungee.getInstance().getLogger().info("Carregadas " + accounts.size() + " contas!");
    }
    
    private static class AccountData {
        String uuid;
        String name;
        String rank;
    }
}
