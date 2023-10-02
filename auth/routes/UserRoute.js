const express = require('express')
const router = express.Router()
const controller = require('../controllers/UserController')
router.get('/:id', controller.get_id)

module.exports = router