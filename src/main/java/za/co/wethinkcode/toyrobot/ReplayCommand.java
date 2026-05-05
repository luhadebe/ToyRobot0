package za.co.wethinkcode.toyrobot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReplayCommand extends Command {
    public ReplayCommand() {
        super("replay", "no arguments");  // Provide a default "no arguments"
    }

    // Constructor with an argument (existing constructor)
    public ReplayCommand(String argument) {
        super("replay", argument);
    }

    @Override
    public boolean execute(Robot target) {
        if(target.getCommandHistory().isEmpty()) {
            target.setStatus("Replay empty");
            return true;
        }

        String dirty_arg = getArgument();
        String replayType = getReplayType(dirty_arg);
        //System.out.println("Replay type: "+ replayType);
        switch (replayType) {
            case "no arguments" -> {
                List<Command> lastCommands = target.getCommandHistory();
                int lastCommandsSize = lastCommands.size();
                List<Command> copyLast = new ArrayList<>(lastCommands);

                for(int i=0;i<lastCommandsSize;i++ ){
                    copyLast.get(i).execute(target);
                    System.out.println(target);
                }
                target.setStatus("replayed " + lastCommandsSize + " commands.");
            }
            case "reversed with arguments" -> {
                String[] clean_arg = dirty_arg.split(" ");
                dirty_arg = clean_arg[1];
                if (dirty_arg.contains("-")) {
                    int max = Math.max(Integer.parseInt(dirty_arg.split("-")[0]), Integer.parseInt(dirty_arg.split("-")[1]));
                    int min = Math.min(Integer.parseInt(dirty_arg.split("-")[0]), Integer.parseInt(dirty_arg.split("-")[1]));


                    List<Command> lastCommands = IntStream.of(max-1,min)
                            .mapToObj(target.getCommandHistory().reversed()::get)
                            .toList();
                    int lastCommandsSize = lastCommands.size()-1;
                    List<Command> copyLast = new ArrayList<>(lastCommands);

                    for(int i=lastCommandsSize;i>=0;i-- ){
                        copyLast.get(i).execute(target);
                        System.out.println(target);
                    }
                    int replayed_num = max - min;
                    target.setStatus("replayed " + replayed_num + " commands.");
                } else {
                    int range = Integer.parseInt(dirty_arg);
                    int size = target.getCommandHistory().size() ;
                    List<Command> lastCommands = target.getCommandHistory().subList(size - range, size).reversed();
                    int lastCommandsSize = lastCommands.size()-1;
                    List<Command> copyLast = new ArrayList<>(lastCommands);
                    for(int i=0;i<=lastCommandsSize;i++ ){
                        copyLast.get(i).execute(target);
                        System.out.println(target);
                    }
                    target.setStatus("replayed " + range + " commands.");
                }
            }
            case "revered with no arguments" -> {
                List<Command> lastCommands = target.getCommandHistory().reversed();
                int lastCommandsSize = lastCommands.size()-1;
                List<Command> copyLast = new ArrayList<>(lastCommands);
                for(int i=0;i<=lastCommandsSize;i++ ){
                    copyLast.get(i).execute(target);
                    System.out.println(target.toString());
                }
                int size = lastCommandsSize +1;
                target.setStatus("replayed " + size + " commands.");
            }
            case "normal" -> {
                if (dirty_arg.contains("-")) {
                    int max = Math.max(Integer.parseInt(dirty_arg.split("-")[0]), Integer.parseInt(dirty_arg.split("-")[1]));
                    int min = Math.min(Integer.parseInt(dirty_arg.split("-")[0]), Integer.parseInt(dirty_arg.split("-")[1]));

//                    List<Command> lastCommands = IntStream.of(max-1,min)
//                            .mapToObj(target.getReplay()::get)
//                            .toList();
                    List<Command> lastCommands = IntStream.of(max-1,min)
                            .mapToObj(target.getCommandHistory().reversed()::get)
                            .toList();
                    int lastCommandsSize = lastCommands.size()-1;
                    List<Command> copyLast = new ArrayList<>(lastCommands);
                    // System.out.println("All commands:\n"+target.getReplay()+"\nNew commands:\n"+lastCommands);
                    for(int i=0;i<=lastCommandsSize;i++ ){
                        copyLast.get(i).execute(target);
                        System.out.println(target);
                    }
                    int replayed_num = max - min;
                    target.setStatus("replayed " + replayed_num + " commands.");
                } else {
                    int range = Integer.parseInt(dirty_arg);
                    int size = target.getCommandHistory().size();
                    List<Command> lastCommands = target.getCommandHistory().subList(size - range, size);
                    int lastCommandsSize = lastCommands.size()-1;
                    List<Command> copyLast = new ArrayList<>(lastCommands);
                    for(int i=0;i<=lastCommandsSize;i++ ){
                        copyLast.get(i).execute(target);
                        System.out.println(target);
                    }
                    target.setStatus("replayed " + range + " commands.");
                }
            }

        }
        return true;
    }

    private String getReplayType(String arg) {

        if(Objects.equals(arg, "no arguments")){
            return "no arguments";
        }

        String[] clean_args = arg.split(" ");

        if(clean_args.length == 2){
            return "reversed with arguments";
        } else {
            if(clean_args[0].contains("reversed")){
                return "revered with no arguments";
            } else {
                return "normal";
            }
        }
    }
}
