/*
 * Copyright 2022 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package software.amazon.smithy.cli.commands;

import static java.lang.String.format;

import software.amazon.smithy.cli.CliError;
import software.amazon.smithy.cli.CliPrinter;
import software.amazon.smithy.cli.Style;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.validation.Severity;
import software.amazon.smithy.model.validation.ValidatedResult;

/**
 * Shares logic for validating a model and printing out events.
 */
final class Validator {

    private Validator() {}

    static void validate(boolean quiet, CliPrinter printer, ValidatedResult<Model> result) {
        long errors = result.getValidationEvents(Severity.ERROR).size();
        long dangers = result.getValidationEvents(Severity.DANGER).size();

        if (!quiet) {
            int warnings = result.getValidationEvents(Severity.WARNING).size();
            int notes = result.getValidationEvents(Severity.NOTE).size();
            long shapeCount = result.getResult().isPresent() ? result.getResult().get().shapes().count() : 0;
            printer.println(format(
                    "Validation result for %d shapes: %s, %s, %s, %s",
                    shapeCount,
                    styledText(printer, "ERROR: ", errors, Style.BRIGHT_RED),
                    styledText(printer, "DANGER: ", dangers, Style.RED),
                    styledText(printer, "WARNING: ", warnings, Style.YELLOW),
                    styledText(printer, "NOTE: ", notes, Style.WHITE)));
        }

        if (!result.getResult().isPresent() || errors + dangers > 0) {
            // Show the error and danger severity events.
            throw new CliError(format("The model is invalid: %s ERROR(s), %d DANGER(s)", errors, dangers));
        }
    }

    private static String styledText(CliPrinter printer, String label, long count, Style style) {
        String result = label + count;
        if (count == 0) {
            return printer.style(result, Style.GREEN);
        } else {
            return printer.style(result, style);
        }
    }
}
