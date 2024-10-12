import express,{Router} from 'express';
const router : Router = express.Router();

import * as adminController from '../../controller/admin/auth.controller';

router.get('/login',adminController.login);

export default router;