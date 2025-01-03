module.exports = {
  "env": {
    "browser": true,
    "es2021": true
  },
  "extends": [
    "plugin:react/recommended",
    "standard-with-typescript"
  ],
  "overrides": [
  ],
  "rules": {
    "@typescript-eslint/consistent-type-assertions": "off",
    "@typescript-eslint/no-non-null-assertion": "off",
    "@typescript-eslint/restrict-template-expressions": "off",
    "@typescript-eslint/strict-boolean-expressions": "off",
    "@typescript-eslint/promise-function-async": "off",
    "@typescript-eslint/no-floating-promises": "off",
    "@typescript-eslint/no-misused-promises": "off",
    "@typescript-eslint/prefer-optional-chain": "off",
    "@typescript-eslint/no-unused-vars": "off",
    "@typescript-eslint/space-before-function-paren": "off",
    "@typescript-eslint/explicit-function-return-type": "off",
    "@typescript-eslint/no-unnecessary-type-assertion": "off",
    "@typescript-eslint/return-await": "off",
    "@typescript-eslint/naming-convention": "off",
    "@typescript-eslint/quotes": "off",
    "@typescript-eslint/no-var-requires": "off",
    "@typescript-eslint/no-confusing-void-expression": "off",
    "@typescript-eslint/dot-notation": "off",
    "@typescript-eslint/restrict-plus-operands": "off"
  },
  "parserOptions": {
    "ecmaVersion": "latest",
    "sourceType": "module",
    "project": ['./tsconfig.json'],
    "tsconfigRootDir": __dirname
  }
}
