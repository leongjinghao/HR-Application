/* eslint-disable no-console */
import { putNewAttendanceInfo } from '../../common/mainTable'
import { LambdaResponse } from '../../utility/responses'
import { log2CloudWatch , error2CloudWatch } from '../../utility/cloudWatch'

/**
 * Create User Attendace With Clock In Timing
 * @param event - User Attendance Details
 * @returns APIGatewayProxyResult
 */
async function createAttendanceInfo(event): Promise<LambdaResponse> {

  // Promise return params
  const apiResponse: LambdaResponse =
  {
    statusCode: 200,
    body: '',
  }
  try {
    event = JSON.parse(event.body)
    const result = await putNewAttendanceInfo(
        event.userId,
        event.date,
        event.clockIn,
        event.location
      )
    apiResponse.body = JSON.stringify(result)
    log2CloudWatch('createAttendanceInfo.ts','createAttendanceInfo',result.message)
  }
  catch (err) {
    apiResponse.statusCode = 500
    apiResponse.body = err
    error2CloudWatch('createAttendanceInfo.ts','createAttendanceInfo',err)
  }

  return apiResponse
}
export const handler = createAttendanceInfo
