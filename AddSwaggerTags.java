import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class AddSwaggerTags {

    private static final Map<String, String[]> MAPPINGS = new HashMap<>();

    static {
        MAPPINGS.put("AuthController", new String[]{"Identity APIs", "Kuldeep Pachori"});
        MAPPINGS.put("AppUserController", new String[]{"Identity APIs", "Kuldeep Pachori"});
        MAPPINGS.put("CurrencyController", new String[]{"Reference Data APIs", "Kuldeep Pachori"});
        MAPPINGS.put("CountryController", new String[]{"Reference Data APIs", "Kuldeep Pachori"});
        MAPPINGS.put("MarketController", new String[]{"Reference Data APIs", "Kuldeep Pachori"});
        MAPPINGS.put("CustomerController", new String[]{"Customer APIs", "Kuldeep Pachori"});
        MAPPINGS.put("AccountController", new String[]{"Account APIs", "Kuldeep Pachori"});

        MAPPINGS.put("FundController", new String[]{"Funds APIs", "Sanket Ganje"});
        MAPPINGS.put("FxRateController", new String[]{"FX APIs", "Sanket Ganje"});
        MAPPINGS.put("FundPriceController", new String[]{"Pricing APIs", "Sanket Ganje"});
        MAPPINGS.put("ImportJobController", new String[]{"Import APIs", "Sanket Ganje"});
        MAPPINGS.put("ImportBatchController", new String[]{"Import APIs", "Sanket Ganje"});
        MAPPINGS.put("ImportItemController", new String[]{"Import APIs", "Sanket Ganje"});

        MAPPINGS.put("PortfolioController", new String[]{"Portfolio APIs", "Rushikesh Mind"});
        MAPPINGS.put("TradeOrderController", new String[]{"Trading APIs", "Rushikesh Mind"});
        MAPPINGS.put("TradeExecutionController", new String[]{"Trading APIs", "Rushikesh Mind"});
        MAPPINGS.put("SettlementController", new String[]{"Settlement APIs", "Rushikesh Mind"});
        MAPPINGS.put("JournalController", new String[]{"Ledger APIs", "Rushikesh Mind"});
        MAPPINGS.put("LedgerAccountController", new String[]{"Ledger APIs", "Rushikesh Mind"});

        MAPPINGS.put("DividendEventController", new String[]{"Dividends APIs", "Mayur Mali"});
        MAPPINGS.put("DividendAllocationController", new String[]{"Dividends APIs", "Mayur Mali"});
        MAPPINGS.put("ReconciliationRunController", new String[]{"Reconciliation APIs", "Mayur Mali"});
        MAPPINGS.put("ReconciliationItemController", new String[]{"Reconciliation APIs", "Mayur Mali"});
        MAPPINGS.put("ReconciliationResolutionController", new String[]{"Reconciliation APIs", "Mayur Mali"});
        MAPPINGS.put("AuditEventController", new String[]{"Audit APIs", "Mayur Mali"});
    }

    public static void main(String[] args) throws IOException {
        Path startPath = Paths.get("e:/wealth-link-platform/src/main/java/com/wealthlink");
        
        Files.walk(startPath)
            .filter(path -> path.toString().endsWith("Controller.java"))
            .forEach(path -> {
                try {
                    processFile(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        System.out.println("Done processing controllers.");
    }

    private static void processFile(Path path) throws IOException {
        String fileName = path.getFileName().toString();
        String className = fileName.replace(".java", "");

        if (!MAPPINGS.containsKey(className)) {
            return;
        }

        String[] tagInfo = MAPPINGS.get(className);
        String domain = tagInfo[0];
        String dev = tagInfo[1];

        String content = new String(Files.readAllBytes(path));

        // Clean up previous tags if present
        content = content.replaceAll("@Tag\\(.*?\\)\\n", "");

        if (!content.contains("import io.swagger.v3.oas.annotations.tags.Tag;")) {
            content = content.replace("import org.springframework.web.bind.annotation.*;", 
                    "import org.springframework.web.bind.annotation.*;\nimport io.swagger.v3.oas.annotations.tags.Tag;");
        }

        String tagAnnotation = String.format("@Tag(name = \"%s\", description = \"Maintained by: %s\")\n@RestController", domain, dev);
        content = content.replace("@RestController", tagAnnotation);

        Files.write(path, content.getBytes());
    }
}
