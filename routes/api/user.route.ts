import express,{Router} from 'express';
const router : Router = express.Router();

import * as userController from '../../controller/api/user.controller';

router.get('/login',userController.login);

export default router;