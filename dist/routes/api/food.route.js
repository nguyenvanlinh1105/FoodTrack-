"use strict";
var __createBinding = (this && this.__createBinding) || (Object.create ? (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    var desc = Object.getOwnPropertyDescriptor(m, k);
    if (!desc || ("get" in desc ? !m.__esModule : desc.writable || desc.configurable)) {
      desc = { enumerable: true, get: function() { return m[k]; } };
    }
    Object.defineProperty(o, k2, desc);
}) : (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    o[k2] = m[k];
}));
var __setModuleDefault = (this && this.__setModuleDefault) || (Object.create ? (function(o, v) {
    Object.defineProperty(o, "default", { enumerable: true, value: v });
}) : function(o, v) {
    o["default"] = v;
});
var __importStar = (this && this.__importStar) || function (mod) {
    if (mod && mod.__esModule) return mod;
    var result = {};
    if (mod != null) for (var k in mod) if (k !== "default" && Object.prototype.hasOwnProperty.call(mod, k)) __createBinding(result, mod, k);
    __setModuleDefault(result, mod);
    return result;
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const router = express_1.default.Router();
const foodController = __importStar(require("../../controller/api/food.controller"));
router.get('/bargain', foodController.bargain);
router.get('/bestseller', foodController.bestseller);
router.get('/new', foodController.newFood);
router.get('/list', foodController.listFood);
router.post('/love', foodController.loveFood);
router.post('/unlove', foodController.unloveFood);
router.get('/detail', foodController.detailFood);
router.get('/love/list', foodController.listLoveFood);
router.get('/list/drink', foodController.listDrink);
router.get('/list/order', foodController.listOrder);
router.get('/list/comment', foodController.listComment);
router.get('/search', foodController.search);
exports.default = router;
