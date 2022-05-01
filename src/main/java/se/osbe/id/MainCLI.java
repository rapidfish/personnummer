package se.osbe.id;

import org.apache.commons.cli.*;
import se.osbe.id.helper.PersonnummerHelper;

import java.util.List;
import java.util.Optional;

public class MainCLI {

    MainCLI(String[] args) {
        Options options = new Options();

        options.addOption(Option.builder("h").longOpt("help").desc("Bring up this help screen").build()); // .required()
        options.addOption(Option.builder("f").longOpt("forgiving").desc("Be forgiving when the checksum (last digit) is wrong").build()); // .required()
        options.addOption(Option.builder("c").longOpt("century").desc("use era and century in output").build()); // .required()
        options.addOption(Option.builder("x").longOpt("extended").desc("View all info about a Personnummer").build()); // .required()

        CommandLineParser parser = new DefaultParser();
        if (args.length == 0) {
            new HelpFormatter().printHelp("Personnummer [pnr] [args]", options);
            exit();
        }

        try {
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);
            List<String> argList = line.getArgList();
            if(line.hasOption("h")){
                new HelpFormatter().printHelp("Personnummer [pnr] [args]", options);
                System.out.println("\nExample usage: java -jar personnummer.jar 460430-0014\n");
                exit();
            }
            if (argList.size() != 1) {
                System.out.println("No personnummer argument given - nothing to do! Use -h to get help");
                exit();
            }
            Optional<Personnummer> pnrOpt = Personnummer.parse(argList.get(0), line.hasOption("f"));
            if (pnrOpt.isEmpty()) {
                System.out.println("Parsing failed.  Reason: Unrecognizable personnummer");
                exit();
            }
            StringBuilder sb = new StringBuilder(line.hasOption("c") ? pnrOpt.get().toString13() : pnrOpt.get().toString());
            if (line.hasOption("x")) {
                //noinspection OptionalGetWithoutIsPresent
                sb.append("\n")
                        .append("Last four digits: ").append(pnrOpt.get().getLastFour()).append("\n")
                        .append("isForgiving: ").append(pnrOpt.get().isForgiving()).append("\n")
                        .append("Correct checksum: ").append(pnrOpt.get().getChecksum()).append("\n")
                        .append("Birth date: ").append(pnrOpt.get().getBirthDate()).append("\n")
                        .append("Age: ").append(pnrOpt.get().getAgeNow()).append("\n")
                        .append("Gender: ").append(pnrOpt.get().getGender()).append("\n")
                        .append("Zodiac sign: ").append(PersonnummerHelper.getZodiacSign(pnrOpt.get()).get().getLatinName()).append("\n")
                        ;
            }
            System.out.println(sb);

        } catch (ParseException exp) {
            System.out.println("Parsing failed.  Reason: " + exp.getMessage());
        }
    }

    public static void main(String[] args) {
        new MainCLI(args);
    }

    private void exit() {
        System.exit(1);
    }
}
