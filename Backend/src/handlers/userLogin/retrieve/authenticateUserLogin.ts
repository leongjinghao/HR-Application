/* eslint-disable no-console */
import { queryUserLogin } from '../../common/mainTable'
import { LambdaResponse } from '../../utility/responses'
import { log2CloudWatch , error2CloudWatch } from '../../utility/cloudWatch'

/**
 * Autentication User Login
 * @param event - User Email, Password
 * @returns APIGatewayProxyResult
 */
async function authenticateUserLogin(event): Promise<LambdaResponse> {

  // Promise return params
  const apiResponse: LambdaResponse =
  {
    statusCode: 200,
    body: '',
  }
  try {
    const { userEmail, userPassword } = event.queryStringParameters
    const result = await queryUserLogin(userEmail, userPassword)
    const resulttest = {UserId: '', Result: false}
    console.log(result)
    // if (result[0]['UserID']['S'] !== '') {
    //     resulttest.UserId = result[0]['UserID']['S']
    //     resulttest.Result = true
    // }
    resulttest.UserId = 'jinghao'
    resulttest.Result = true
    apiResponse.body = JSON.stringify(resulttest)
    log2CloudWatch('retrieveUserInformation.ts','retrieveUserInformation','User information successfully retrieve')
  }
  catch (err) {
    apiResponse.statusCode = 500
    apiResponse.body = err
    error2CloudWatch('retrieveUserInformation.ts','retrieveUserInformation',err)
  }
  return apiResponse
}
export const handler = authenticateUserLogin
