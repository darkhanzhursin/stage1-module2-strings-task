package com.epam.mjc;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */

    MethodSignature methodSignature;
    List<MethodSignature.Argument> arguments = new ArrayList<>();
    public MethodSignature parseFunction(String signatureString) {
        int openIndex = signatureString.indexOf("(");

        String tillArgs = signatureString.substring(0, openIndex);
        StringTokenizer tokenizer = new StringTokenizer(tillArgs, " ");
        int countTokens = tokenizer.countTokens();
        List<String> stringList = List.of(tillArgs.split(" "));

        String splitArgs = signatureString.substring(openIndex+1);
        List<String> args = List.of(splitArgs.split("[(),]+"));

        if (!args.isEmpty()) {
            for (int i = 0; i < args.size(); i++) {
                String eachArgsLine = args.get(i).trim();
                String[] subArgs = eachArgsLine.split(" ");
                arguments.add(new MethodSignature.Argument(subArgs[0].trim(),
                    subArgs[1].trim()));
            }
            if (countTokens > 2) {
                methodSignature = new MethodSignature(stringList.get(2), arguments);
                methodSignature.setReturnType(stringList.get(1));
                methodSignature.setAccessModifier(stringList.get(0));
            } else {
                methodSignature = new MethodSignature(stringList.get(1), arguments);
                methodSignature.setReturnType(stringList.get(0));
            }
        } else {
            if (countTokens > 2) {
                methodSignature = new MethodSignature(stringList.get(2));
                methodSignature.setReturnType(stringList.get(1));
                methodSignature.setAccessModifier(stringList.get(0));
            } else {
                methodSignature = new MethodSignature(stringList.get(1));
                methodSignature.setReturnType(stringList.get(0));
            }
        }

        return methodSignature;
    }
}
