import os
import re

DEV1 = "Kuldeep Pachori"
DEV2 = "Sanket Ganje"
DEV3 = "Rushikesh Mind"
DEV4 = "Mayur Mali"

MAPPINGS = {
    "AuthController": ("Identity", DEV1),
    "AppUserController": ("Identity", DEV1),
    "CurrencyController": ("Reference Data", DEV1),
    "CountryController": ("Reference Data", DEV1),
    "MarketController": ("Reference Data", DEV1),
    "CustomerController": ("Customer", DEV1),
    "AccountController": ("Account", DEV1),
    
    "FundController": ("Funds", DEV2),
    "FxRateController": ("FX", DEV2),
    "FundPriceController": ("Pricing", DEV2),
    "ImportJobController": ("Import", DEV2),
    "ImportBatchController": ("Import", DEV2),
    "ImportItemController": ("Import", DEV2),
    
    "PortfolioController": ("Portfolio", DEV3),
    "TradeOrderController": ("Trading", DEV3),
    "TradeExecutionController": ("Trading", DEV3),
    "SettlementController": ("Settlement", DEV3),
    "JournalController": ("Ledger", DEV3),
    "LedgerAccountController": ("Ledger", DEV3),
    
    "DividendEventController": ("Dividends", DEV4),
    "DividendAllocationController": ("Dividends", DEV4),
    "ReconciliationRunController": ("Reconciliation", DEV4),
    "ReconciliationItemController": ("Reconciliation", DEV4),
    "ReconciliationResolutionController": ("Reconciliation", DEV4),
    "AuditEventController": ("Audit", DEV4),
}

base_path = "e:/wealth-link-platform/src/main/java/com/wealthlink"

for root, _, files in os.walk(base_path):
    for file in files:
        if file.endswith("Controller.java"):
            file_path = os.path.join(root, file)
            class_name = file.replace(".java", "")
            
            if class_name in MAPPINGS:
                domain, dev = MAPPINGS[class_name]
                
                with open(file_path, "r", encoding="utf-8") as f:
                    content = f.read()
                    
                if "io.swagger.v3.oas.annotations.tags.Tag" in content:
                    continue
                    
                # Add import
                content = content.replace("import org.springframework.web.bind.annotation.*;", 
                                          "import org.springframework.web.bind.annotation.*;\nimport io.swagger.v3.oas.annotations.tags.Tag;")
                
                # Add tag
                tag_annotation = f'@Tag(name = "{domain} APIs", description = "Maintained by: {dev}")\n@RestController'
                content = content.replace("@RestController", tag_annotation)
                
                with open(file_path, "w", encoding="utf-8") as f:
                    f.write(content)
                    
print("Done")
