package scripting;

import javax.swing.*;

/**
 * @author Smoke
 */
public class ScriptSysFunc {

    private Script pScript;
    /** do not modify below **/
    private Object pResult = null;
    private final Object pResultMonitor = new Object();
    private boolean bIsSuspended = false;
    /** safe to modify below this point**/
    private long tDelayTimeout = 20000; // 20 seconds in case you accidentally made it wait forever

    public ScriptSysFunc(Script pScript) {
        this.pScript = pScript;
    }

    public void PrintToConsole(Object sText) {
        System.out.println(sText.toString());
    }

    public int AskMenu(Object sMessage, Object[] aSelection) {
        String s = AskMenuGetString(sMessage.toString(), aSelection);
        int nSel = 0;
        if ((s != null) && (s.length() > 0)) {
            for (int i = 0; i < aSelection.length; i++) {
                if (aSelection[i] == s) {
                    nSel = i;
                    break;
                }
            }
        }
        return nSel;
    }

    public String AskMenuGetString(Object sMessage, Object[] aSelection) {
        return (String) JOptionPane.showInputDialog(
                new JFrame(),
                sMessage.toString(),
                "Customized Dialog",
                JOptionPane.PLAIN_MESSAGE,
                null,
                aSelection,
                aSelection[0]);
    }

    public int AskNumber(Object sMessage) {
        return Integer.parseInt(AskText(sMessage));
    }

    public int AskNumber(Object sMessage, int nMinimum, int nMaximum) {
        return Integer.parseInt(AskText(sMessage));
    }

    public String AskText(Object sMessage) {
        return JOptionPane.showInputDialog(sMessage.toString());
    }

    public int AskAccept(Object sText) {
        return JOptionPane.showConfirmDialog(
                new JFrame(),
                sText.toString(),
                "AskAccept - Confirmation Dialog",
                JOptionPane.YES_NO_OPTION) == 0 ? 1 : 0;
    }

    public int AskYesNo(Object sText) {
        return JOptionPane.showConfirmDialog(
                new JFrame(),
                sText.toString(),
                "AskYesNo - Confirmation Dialog",
                JOptionPane.YES_NO_OPTION) == 0 ? 1 : 0;
    }

    public int Say(Object sText) {
        return JOptionPane.showConfirmDialog(
                new JFrame(),
                sText.toString(),
                "Say - Regular Dialog",
                JOptionPane.OK_CANCEL_OPTION);
    }

    public int SayBoxChat(Object sText) {
        return JOptionPane.showConfirmDialog(
                new JFrame(),
                sText.toString(),
                "Illustration - Big Dialog",
                JOptionPane.OK_CANCEL_OPTION);
    }

    /** do not modify below **/

    public void Notify(Object o) {
        synchronized (pResultMonitor) {
            if (this.bIsSuspended) {
                this.pResult = o == null ? -1 : o;
                this.pResultMonitor.notify();
            }
        }
    }

    public void Wait() {
        Wait(-1);
    }

    public void Wait(long tTimeout) {
        synchronized (pResultMonitor) {
            this.bIsSuspended = true;
            try {
                if (tTimeout > 0) {
                    pResultMonitor.wait(tTimeout);
                } else {
                    if (this.tDelayTimeout > 0) {
                        pResultMonitor.wait(this.tDelayTimeout);
                    } else {
                        pResultMonitor.wait();
                    }
                }
            } catch (InterruptedException | IllegalMonitorStateException e) {
                if (bIsSuspended && (tTimeout > 0 || this.tDelayTimeout > 0)) {
                    Notify(-1);
                }
            } finally {
                this.bIsSuspended = false;
                this.tDelayTimeout = 0;
            }
        }
    }
}
