const express = require('express')
const router = express.Router()
const controller = require('../controllers/UserController')
router.get('/:id', controller.get_id)
router.get('/find/:name', controller.findByName)
module.exports = router