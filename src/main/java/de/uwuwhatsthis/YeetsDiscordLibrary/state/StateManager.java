package de.uwuwhatsthis.YeetsDiscordLibrary.state;

import de.uwuwhatsthis.YeetsDiscordLibrary.errors.InvalidArgumentError;
import de.uwuwhatsthis.YeetsDiscordLibrary.events.EventListener;
import de.uwuwhatsthis.YeetsDiscordLibrary.httpEndpoint.genericRequest.GenericHttpRequest;
import de.uwuwhatsthis.YeetsDiscordLibrary.httpEndpoint.requests.RegisterSlashCommandRequest;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.application.SelfApplication;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.commands.SlashCommand;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.commands.argument.Argument;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.guild.Guild;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Debugger;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Helper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StateManager {

    private static final Debugger debugger = new Debugger("StateManager");

    private static StateManager instance;

    private SelfApplication selfApplication;

    private Map<String, Guild> cachedGuilds;

    private ExecutorService executorService;

    private List<EventListener> eventListeners;

    private List<SlashCommand> slashCommandList;

    private String token;

    public StateManager(){
        instance = this;
        selfApplication = null;
        cachedGuilds = new HashMap<>();
        eventListeners = new ArrayList<>();
        slashCommandList = new ArrayList<>();
        executorService = Executors.newFixedThreadPool(10);
    }

    public static StateManager getInstance(){
        if (instance == null){
            instance = new StateManager();
        }

        return instance;
    }

    public void shutdownExecutor(){
        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)){
                executorService.shutdownNow();
            }

        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    public void addGuild(Guild guild){
        guild.getGuildId().ifPresent(id -> cachedGuilds.put(id, guild));
    }

    public Optional<Guild> getGuildById(String id){
        return Optional.ofNullable(cachedGuilds.get(id));
    }

    public void addEventListener(EventListener eventListener){
        eventListeners.add(eventListener);
    }

    public void addEventListeners(EventListener... eventListeners){
        this.eventListeners.addAll(Arrays.asList(eventListeners));
    }

    public List<EventListener> getEventListeners(){
        return this.eventListeners;
    }

    public SelfApplication getApplication() {
        return selfApplication;
    }

    public void setApplication(SelfApplication selfApplication) {
        this.selfApplication = selfApplication;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void addSlashCommand(SlashCommand command){
        slashCommandList.add(command);
    }

    public void registerSlashCommands(){
        for (SlashCommand slashCommand : slashCommandList) {
            String commandName = slashCommand.getCommandName();
            String description = slashCommand.getDescription();

            JSONArray args = new JSONArray();

            for (Argument argument : slashCommand.getArguments()) {
                try {
                    args.put(Helper.getArgument(argument));
                } catch (InvalidArgumentError e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }

            JSONObject slashCommandData = new JSONObject();

            slashCommandData.put("name", commandName);
            slashCommandData.put("description", description);
            slashCommandData.put("type", 1);

            if (!args.isEmpty()){
                slashCommandData.put("options", args);
            }

            debugger.debug("About to register slash command with name: " + commandName);

            RegisterSlashCommandRequest request = new RegisterSlashCommandRequest(slashCommand.isGuild() ? slashCommand.getGuildID() : null, slashCommandData, System.out::println);
            request.doRequest();

            debugger.debug("Registered slash command with name: " + commandName);
        }
    }
}
