import { APIGatewayProxyResult } from 'aws-lambda'

export interface LambdaResponse extends APIGatewayProxyResult {
  statusCode: 200 | 400 | 401 | 500;
  body: string;
}

export const responseMsg = {
  _200: 'OK',
  _400: 'Invalid parameters',
  _401: 'Authentication has failed',
  _400_VALIDATION_ERROR: 'Event object failed validation',
  _500: 'An error has occured',
}

export const response200: LambdaResponse = {
  statusCode: 200,
  body: responseMsg._200,
}

export const response400: LambdaResponse = {
  statusCode: 400,
  body: responseMsg._400,
}

export const response401: LambdaResponse = {
  statusCode: 401,
  body: responseMsg._401,
}

export const response500: LambdaResponse = {
  statusCode: 500,
  body: responseMsg._500,
}
