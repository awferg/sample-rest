/*
 * TestConstants.java
 *
 * @formatter:off
 *
 *
 * @author Andrew
 *
 * Created: 19 Jan 2019
 *
 * @formatter:on
 */

package com.gitbub.awferg.restex;

import com.github.awferg.restex.model.EncryptedUser;
import com.github.awferg.restex.model.User;

import java.util.Collections;

public class TestConstants {

    public static final String BIG_NAME_FIRSTNAME = String.join("", Collections.nCopies(255, "F"));

    public static final String BIG_NAME_SURNAME = String.join("", Collections.nCopies(255, "S"));
    public static final String BIG_EMAIL = String.join("", Collections.nCopies(255, "E"));
    public static final String BIG_AGE = String.join("", Collections.nCopies(255, "A"));
    public static final int BIG_AGE_NUMERIC = 150;
    public static final EncryptedUser USER_ENCRYPTED =
            new NonChangeableEncryptedUser(TestConstants.BIG_NAME_FIRSTNAME,
                    TestConstants.BIG_NAME_SURNAME, TestConstants.BIG_EMAIL, TestConstants.BIG_AGE);

    public static final User USER = new User(TestConstants.BIG_NAME_FIRSTNAME,
            TestConstants.BIG_NAME_SURNAME, TestConstants.BIG_EMAIL, TestConstants.BIG_AGE_NUMERIC);

    private TestConstants() {
        // static constants only
    }
}


