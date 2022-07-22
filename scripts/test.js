nPauseSeconds = 1;

System = Java.type("java.lang.System"); // you can also import things directly from Java, which is awesome
System.out.println("Oi, from java imported method \n");

function StartScript() {
    pUser.SetNexonCash(10000); // start with 10k
    for (i = 1; i <= 3; i++) {
        sPlaceName = GetPlaceName(i);
        self.Say("Hello for the " + sPlaceName + " time! Going to loop 3 times.");
        // you can choose to add a pause here manually like this
        nCurrentNexonCash = pUser.GetNexonCash();
        nRandomIncrease = Math.floor(Math.random() * (1000 * i));
        self.Say("\tIncreasing player's NX by:  " + nRandomIncrease + " Nexon Cash");
        pUser.SetNexonCash(nCurrentNexonCash + nRandomIncrease);
        self.Say("\tPlayer now has a total of:  " + pUser.GetNexonCash() + " Nexon Cash");
        self.Wait((1000 * nPauseSeconds)); // in a real script, it pauses until the user proceeds, and we don't use this
    }
}

function GetPlaceName(nPlace) {
    nDigit = nPlace;
    if (nPlace > 10) {
        nDigit = nPlace % 10;
    }
    if (nDigit == 1) {
        return nPlace.toString() + "st";
    }
    if (nDigit == 2) {
        return nPlace.toString() + "nd";
    }
    if (nDigit == 3) {
        return nPlace.toString() + "rd";
    }
    return nPlace.toString() + "th";
}

// Anything not in a "function" is in the global scope, and is executed from top to bottom
StartScript();
self.Say("This one will print after all 10 of the prints from StartScript()");

// Because scripts execute in 1-single-thread, top to bottom read order, it is stuck in the function until
// that function no longer needs the script's thread (ie, after the 10 prints)