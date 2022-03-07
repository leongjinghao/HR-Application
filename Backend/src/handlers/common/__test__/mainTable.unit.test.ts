/* eslint-disable no-console */
import { createLeave } from '../mainTable';
import { resultMessageResponseTypeDatabase } from '../../utility/dataType'

console.log = jest.fn;
console.error = jest.fn;

beforeEach(() => {
    jest.clearAllMocks();
  });

/*describe('/common/mainTable mainTable.ts test', () => {
    test('Create User Leave successfully', async () => {
        const matchResponse : resultMessageResponseTypeDatabase = { 
            result: true, 
            message: 'User leave successfully created.' 
        };
        const response = await createLeave( 
            "abc",
            "12122222/1232122",
            "MC",
            "Ali",
            "nil",
            "Pending")
        expect(response.result).toStrictEqual(matchResponse.result)
        expect(response.message).toStrictEqual(matchResponse.message)
    });
}); */