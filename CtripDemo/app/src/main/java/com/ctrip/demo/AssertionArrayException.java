package com.ctrip.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liumeng on 2018/5/30.
 */

public class AssertionArrayException extends Exception{
    private static final long serialVersionUID = 1;
    private List<Hack.HackDeclaration.HackAssertionException> mAssertionErr;

    public AssertionArrayException(String str) {
        super(str);
        this.mAssertionErr = new ArrayList();
    }

    public void addException(Hack.HackDeclaration.HackAssertionException hackAssertionException) {
        this.mAssertionErr.add(hackAssertionException);
    }

    public void addException(List<Hack.HackDeclaration.HackAssertionException> list) {
        this.mAssertionErr.addAll(list);
    }

    public List<Hack.HackDeclaration.HackAssertionException> getExceptions() {
        return this.mAssertionErr;
    }

    public static AssertionArrayException mergeException(AssertionArrayException assertionArrayException, AssertionArrayException assertionArrayException2) {
        if (assertionArrayException == null) {
            return assertionArrayException2;
        }
        if (assertionArrayException2 == null) {
            return assertionArrayException;
        }
        AssertionArrayException assertionArrayException3 = new AssertionArrayException(assertionArrayException.getMessage() + assertionArrayException2.getMessage());
        assertionArrayException3.addException(assertionArrayException.getExceptions());
        assertionArrayException3.addException(assertionArrayException2.getExceptions());
        return assertionArrayException3;
    }

    public String toString() {

        return "exception";
    }

}
