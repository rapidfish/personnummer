package se.osbe.id.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.*;
import se.osbe.id.Personnummer;
import se.osbe.id.helper.PersonnummerHelper;
import se.osbe.id.vo.PnrInfoVO;

import java.util.List;
import java.util.Optional;

public class MainCLI {

    private final static String CLI_VERSION = "2.1-beta";

    MainCLI(String[] args) throws JsonProcessingException {
        String output = "n/a";
        PnrInfoVO result = null;
        Options options = new Options();

        options.addOption(Option.builder("h").longOpt("help").desc("Bring up this help screen").build()); // .required()
        options.addOption(Option.builder("f").longOpt("forgiving").desc("Be forgiving when the checksum (last digit) is wrong").build()); // .required()
        options.addOption(Option.builder("c").longOpt("century").desc("use era and century in output").build()); // .required()
        options.addOption(Option.builder("x").longOpt("extended").desc("View all info about a Personnummer").build()); // .required()
        options.addOption(Option.builder("v").longOpt("version").desc("Show version of this command").build()); // .required()
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
                exit();
            }

            if (line.hasOption("v")) {
                System.out.println("Version " + CLI_VERSION);
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
            Personnummer pnr = pnrOpt.get();
            if (line.hasOption("x")) {
                result = new PnrInfoVO(
                        pnr.toString10(),
                        pnr.toString11(),
                        pnr.toString12(),
                        pnr.toString13(),
                        pnr.getLastFour(),
                        pnr.isForgiving(),
                        pnr.getChecksum(),
                        pnr.getBirthDate().toString(),
                        pnr.getAgeNow(),
                        pnr.getDaysSinceBirth(),
                        pnr.getGender(),
                        PersonnummerHelper.getZodiacSign(pnr).get().getLatinName(),
                        PersonnummerHelper.getZodiacSign(pnr).get().getSwedishName(),
                        "Year of the " + PersonnummerHelper.getTypeForYear(pnr.getBirthDate()).getAnimalName(),
                        PersonnummerHelper.getTypeForYear(pnr.getBirthDate()).getAnimalNameSwe() + "s år",
                        pnr.getIDType(),
                        pnr.toString()
                );
                output = (line.hasOption("j")) ?
                        new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString((PnrInfoVO) result) :
                        result.toString();
            } else {
                String pnrStr = line.hasOption("c") ? pnr.toString13() : pnr.toString11();
                output = (line.hasOption("j")) ? "{ 'personnummer' : '" + pnrStr + "' }" : pnrStr;
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
