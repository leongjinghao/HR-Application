import APIGatewayEventRequestContext from 'aws-sdk';

export interface APIGatewayProxyEvent {
  body: string | null;
  headers: { [name: string]: string };
  multiValueHeaders: { [name: string]: string[] };
  httpMethod: string;
  isBase64Encoded: boolean;
  path: string;
  pathParameters: { [name: string]: string };
  queryStringParameters: { [name: string]: string } | null;
  multiValueQueryStringParameters: { [name: string]: string[] } | null;
  stageVariables: { [name: string]: string } | null;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  requestContext: any;
  resource: string;
}

export interface resultMessageResponseTypeDatabase {
  /** Whether the request is successful */
  result: boolean,
  /** Response information */
  message: string
}

/**
 * APIGatewayProxyEvent Attributes  
 * @param eventData Event data which passed from the front end 
 * @returns APIGatewayProxyEvent
 */
export const mockData = async (
  eventData
) => {
  const mockEvent: APIGatewayProxyEvent = {
    body: JSON.stringify(eventData),
    headers: {},
    multiValueHeaders: {},
    httpMethod: '',
    isBase64Encoded: false,
    path: '',
    pathParameters: {},
    queryStringParameters: {},
    multiValueQueryStringParameters: {},
    stageVariables: {},
    requestContext: APIGatewayEventRequestContext,
    resource: '',
  }
  return mockEvent;
}