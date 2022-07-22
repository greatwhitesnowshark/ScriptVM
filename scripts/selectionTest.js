function StartTrySelectionScript() {
    if (self.AskYesNo("Do you want to continue?")) {
        if (self.AskYesNo("Enter a custom selection number?")) {
            nMinimum = 1;
            nMaximum = 999;
            nHowMany = self.AskNumber("How many did you want?", nMinimum /*minimum*/, nMaximum/*maximum*/);
            if (nHowMany < 1 || nHowMany > 999) {
                self.Say("You can't get that many, sorry! Only between " + nMinimum + " and " + nMaximum + " allowed!");
                return;
            } else {
                self.Say("I'm happy to give you " + nHowMany + "!! Here you go!");
            }
        }

        nHowMany = self.AskMenu(
            "What would you like to do?",
            ["Go for a walk", "Code something indoors", "Eat a ton of food", "Pass out drunk"]
        );

        self.Say("You chose selection #" + nHowMany + " - please proceed at your leisure.");

        switch (nHowMany) {
            case 0:
                self.Say("Option 0, the first choice.");
                break;
            case 1:
                self.Say("Option 1, the second choice.");
                break;
            case 2:
                self.Say("Option 2, the third choice.");
                break;
            case 3:
                self.Say("Option 3, the fourth choice.");
                break;
            default:
                self.Say("The option type wasn't handled in the switch statement, so it reached the \"default\" clause.");
                break;
        }
    } else {
        self.Say("You didn't continue. Shucks.");
    }
}


StartTrySelectionScript(); // This starts the execution.