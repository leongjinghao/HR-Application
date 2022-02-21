/* eslint-disable no-console */
const genPrintFormat = (fileName: string, funcName: string, message: string) => {
  return `${fileName} - ${funcName} -> ${message}`;
}

/**
 * Log information into cloudwatch
 * @param fileName The location of the function
 * @param funcName The function name
 * @param message  Log Message
 */
export const log2CloudWatch = (
  fileName: string,
  funcName: string,
  message: string,
) => {
  console.log(genPrintFormat(fileName, funcName, message));
}

/**
 * Log error into cloudwatch
 * @param fileName The location of the function
 * @param funcName The function name
 * @param message Error Message
 */
export const error2CloudWatch = (
  fileName: string,
  funcName: string,
  message: string,
) => {
  console.error(genPrintFormat(fileName, funcName, message));
}
