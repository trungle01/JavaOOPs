/*
    4/14: Added in so we could get rid of PlaguePanel
 */
package simstation;

import mvc.*;

public interface SimFactory extends AppFactory{
    public View getView(Model m);
}
