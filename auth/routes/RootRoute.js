const express = require('express')
const router = express.Router()
const controller = require('../controllers/RootController')
router.get('/login', controller.login)
router.get('/api/callback', controller.oauth_callback)
router.get('/verify', controller.verify)

module.exports = router