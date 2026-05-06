package com.example.bank;

// =============================================================================
// BAD_STYLE_EXAMPLE.java
// PURPOSE: Intentionally violates Checkstyle rules to demonstrate PR bot.
//
// This file is pushed on a FEATURE BRANCH (not main).
// When a Pull Request is opened, dbelyaev/action-checkstyle will:
//   1. Detect all violations below
//   2. Post inline comments on EACH offending line in the PR diff
//   3. The "Test & Build" status check will FAIL
//   4. The Merge button stays LOCKED until violations are fixed.
//
// VIOLATIONS IN THIS FILE:
//   Line 28: Missing class Javadoc                (MissingJavadocType)
//   Line 28: Class name not PascalCase            (TypeName)
//   Line 30: Public field – breaks encapsulation  (VisibilityModifier)
//   Line 31: Field name has underscore            (MemberName)
//   Line 32: Constant not UPPER_SNAKE_CASE        (ConstantName)
//   Line 36: Missing method Javadoc               (MissingJavadocMethod)
//   Line 37: Missing braces on if                 (NeedBraces)
//   Line 40: Missing braces on if-else            (NeedBraces)
//   Line 44: Uses tab indentation                 (FileTabCharacter)
// =============================================================================

// VIOLATION 1: Missing class Javadoc (MissingJavadocType)
// VIOLATION 2: Class name uses underscore – should be BadStyleExample (TypeName)
class bad_style_example {

    // VIOLATION 3: public field – VisibilityModifier
    public String accountName;

    // VIOLATION 4: field name has underscore – MemberName
    private double account_balance;

    // VIOLATION 5: constant name is camelCase – ConstantName (must be UPPER_SNAKE_CASE)
    private static final double defaultRate = 0.05;

    // VIOLATION 6: Missing Javadoc on public method – MissingJavadocMethod
    public double getBalance() {
        // VIOLATION 7: missing braces on if – NeedBraces
        if (account_balance < 0)
            return 0;

        // VIOLATION 8: missing braces on if-else – NeedBraces
        if (account_balance > 1000)
            return account_balance * (1 + defaultRate);
        else
            return account_balance;
    }

    // VIOLATION 9: TAB indentation (FileTabCharacter)
	public void setBalance(double val) {
		this.account_balance = val;
	}
}
