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
    const response = {UserId: '', Result: false}
    console.log(result)
    if (result.length === 1) {
      response.UserId = result[0].UserId.S!
      response.Result = true
    }
    apiResponse.body = JSON.stringify(response)
    log2CloudWatch('authenticateUserLogin.ts','authenticateUserLogin','User authenticate successfully retrieve')
  }
  catch (err) {
    apiResponse.statusCode = 500
    apiResponse.body = err
    error2CloudWatch('authenticateUserLogin.ts','authenticateUserLogin',err)
  }
  return apiResponse
}
export const handler = authenticateUserLogin
