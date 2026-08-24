package io.github.geniyyc.mathface.common.exceptions

import io.github.geniyyc.mathface.common.models.MfCommand

class UnknownMfCommand(command: MfCommand) : Throwable("Wrong command $command at mapping toTransport stage")
