package se.osbe.id;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.*;
import se.osbe.id.helper.PersonnummerHelper;

import java.util.List;
import java.util.Optional;

public class MainCLI {

    private final static String VERSION = "1.0";
    private ObjectMapper objectMapper;

    MainCLI(String[] args) throws JsonProcessingException {
        String output = "n/a";
        PnrCliDao result = null;
        Options options = new Options();

        options.addOption(Option.builder("h").longOpt("help").desc("Bring up this help screen").build()); // .required()
        options.addOption(Option.builder("f").longOpt("forgiving").desc("Be forgiving when the checksum (last digit) is wrong").build()); // .required()
        options.addOption(Option.builder("c").longOpt("century").desc("use era and century in output").build()); // .required()
        options.addOption(Option.builder("x").longOpt("extended").desc("View all info about a Personnummer").build()); // .required()
        options.addOption(Option.builder("v").longOpt("version").desc("Show command version").build()); // .required()
        options.addOption(Option.builder("j").longOpt("json").desc("Show output as JSON").build()); // .required()

        CommandLineParser parser = new DefaultParser();
        if (args.length == 0) {
            new HelpFormatter().printHelp("personnummer [args] [pnr]", options);
            exit();
        }

        try {
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);
            List<String> argList = line.getArgList();

            if (line.hasOption("h")) {
                new HelpFormatter().printHelp("personnummer [pnr] [args]", options);
                System.out.println("\nExample usage:\n");
                System.out.println("personnummer -century -json -extended 460430-0014\n");
                System.out.println("or...\n");
                System.out.println("personnummer -cjx 460430-0014\n");
                exit();
            }

            if (line.hasOption("v")) {
                System.out.println("Version " + VERSION);
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

            // Happy flow from here ...

            if (line.hasOption("x")) {
                result = new PnrCliDao();
                result.setPersonnummer10(pnrOpt.get().toString10());
                result.setPersonnummer11(pnrOpt.get().toString11());
                result.setPersonnummer12(pnrOpt.get().toString12());
                result.setPersonnummer13(pnrOpt.get().toString13());
                result.setLastFourDigits(pnrOpt.get().getLastFour());
                result.setForgiving(pnrOpt.get().isForgiving());
                result.setCorrectChecksum(pnrOpt.get().getChecksum());
                result.setBirthDate(pnrOpt.get().getBirthDate().toString());
                result.setAge(pnrOpt.get().getAgeNow());
                result.setGender(pnrOpt.get().getGender());
                result.setZodiacSign(PersonnummerHelper.getZodiacSign(pnrOpt.get()).get().getLatinName());
                result.setZodiacSignSwe(PersonnummerHelper.getZodiacSign(pnrOpt.get()).get().getSwedishName());

                output = (line.hasOption("j")) ?
                        new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString((PnrCliDao) result) :
                        result.toString();
            } else {
                String pnr = line.hasOption("c") ? pnrOpt.get().toString13() : pnrOpt.get().toString11();
                output = (line.hasOption("j")) ? "{ 'personnummer' : '" + pnr + "' }" : pnr;
            }

            System.out.println(output);

        } catch (ParseException exp) {
            System.out.println("Parsing failed.  Reason: " + exp.getMessage());
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        new MainCLI(args);
    }

    private void exit() {
        System.exit(1);
    }
}
